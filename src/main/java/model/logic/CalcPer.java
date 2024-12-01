package model.logic;

import jakarta.servlet.http.HttpServletRequest;

import model.Player;
import model.SessionBoard;
import model.SessionRegulation;
import model.role.person.Role;

public class CalcPer {
	private SessionRegulation sr;
	private SessionBoard sb;
	private float latentWWs;

	public CalcPer(HttpServletRequest request) {
		this.sr = (SessionRegulation) request.getSession().getAttribute("sr");
		this.sb = (SessionBoard) request.getSession().getAttribute("sb");
	}

	public void updateVillsPer() {
		latentWWs = sr.getWwsList().size();
		System.out.println("1."+latentWWs);
		updateCoTruePer();
		updateNotCoVillsPer();
		updateWwsPer();
		System.out.println("2."+latentWWs);
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
