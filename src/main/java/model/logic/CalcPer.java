package model.logic;

import java.io.Serializable;
import java.util.List;

import model.FaseBoard;
import model.Player;
import model.SessionRegulation;

public class CalcPer implements Serializable{
	private static SessionRegulation sr;
	private FaseBoard fb;
	private float latentWWs;
	
	public CalcPer() {
		
	}

	public CalcPer(FaseBoard sb) {
		this.fb = sb;
	}

	public void updateVillsPer() {
		latentWWs = sr.getWwsList().size();
		updateLatentPlayerPer();
		updateWwsPer();
	}

	public void updateLatentPlayerPer() {
		float wwsPer = latentWWs / fb.getLatentPlayerList().size();
		float villsPer = 1 - wwsPer;
		for (Player notCoPlayer : fb.getLatentPlayerList()) {
			notCoPlayer.setVillsPer(villsPer);
		}
	}

	public void updateWwsPer() {
		for (Player player : fb.getPlayerList()) {
			player.setWwsPer(1 - player.getVillsPer());
		}
	}
	
	public int countConfDeadWws(List<Player> playerList) {
		float temp = playerList.stream()
				.filter(p -> !p.isAlive())
				.map(p -> p.getWwsPer())
				.reduce(0.0f, (a, b) -> a + b);

		temp = (int) Math.floor(temp);
		return (int) temp;
	}
	
	
	
	
	public String perToJsp(float value) {
        return String.format("%.1f", value * 100);
    }
	
	public static void setSr(SessionRegulation SR) {
		sr=SR;
	}
}
