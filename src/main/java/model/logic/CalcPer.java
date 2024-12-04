package model.logic;

import model.Player;
import model.SessionBoard;
import model.SessionRegulation;

public class CalcPer {
	private static SessionRegulation sr;
	private SessionBoard sb;
	private float latentWWs;

	public CalcPer(SessionBoard sb) {
		this.sb = sb;
	}

	public void updateVillsPer() {
		latentWWs = sr.getWwsList().size();
		updateLatentPlayerPer();
		updateWwsPer();
	}

	public void updateLatentPlayerPer() {
		float wwsPer = latentWWs / sb.getLatentPlayerList().size();
		float villsPer = 1 - wwsPer;
		for (Player notCoPlayer : sb.getLatentPlayerList()) {
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
	
	public static void setSr(SessionRegulation SR) {
		sr=SR;
	}
}
