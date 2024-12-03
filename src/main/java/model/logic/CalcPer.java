package model.logic;

import model.Player;
import model.SessionBoard;
import model.SessionRegulation;
import model.role.person.Role;

public class CalcPer {
	private SessionRegulation sr;
	private SessionBoard sb;
	private float latentWWs;

	public CalcPer(SessionRegulation sr,SessionBoard sb) {
		this.sr = sr;
		this.sb = sb;
	}

	public void updateVillsPer() {
		latentWWs = sr.getWwsList().size();
		updateCoTruePer();
		updateNotCoVillsPer();
		updateWwsPer();
	}

	public void updateCoTruePer() {
		for (Role co : sb.getCoPlayerMap().keySet()) {
			if (sb.getCoPlayerMap().get(co).size() >= sr.getRoleSizeMap().get(co)) {
				latentWWs -= sb.getCoPlayerMap().get(co).size() - sr.getRoleSizeMap().get(co);

				float truePer = sr.getRoleSizeMap().get(co) / sb.getCoPlayerMap().get(co).size();
				for (Player coPlayer : sb.getCoPlayerMap().get(co)) {
					coPlayer.getRolePerMap().put(co, truePer);
					coPlayer.getRolePerMap().replaceAll((key, value) -> key.equals(co) ? value : 0);
					coPlayer.setVillsPer(truePer);
				}
			}
		}
	}

	public void updateNotCoVillsPer() {
		float wwsPer = latentWWs / sb.getNotCoPlayerList().size();
		float villsPer = 1 - wwsPer;
		for (Player notCoPlayer : sb.getNotCoPlayerList()) {
			notCoPlayer.setVillsPer(villsPer);
		}
	}

	public void updateWwsPer() {
		for (Player player : sb.getPlayerList()) {
			player.setWwsPer(1 - player.getVillsPer());
		}
	}
	
	public String perToJsp(float value) {
        return String.format("%.1f", value * 100);
    }
}
