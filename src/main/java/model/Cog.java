package model;

import java.util.List;
import java.util.stream.Collectors;

import model.role.person.Role;

public class Cog {
	static SessionRegulation sr;
	FaseBoard sb;
	Role role;
	List<Player> playerList;

	int size;
	int trueSize;
	int hasWws;

	int confDeadWws;

	public Cog(FaseBoard sb, Role role) {
		this.sb = sb;
		this.role = role;
		criatePlayerList();
		updateSizes();
		updateTruePer();
		updateAliveWws();

	}

	void criatePlayerList() {
		playerList = sb.getPlayerList().stream()
				.filter(a -> a.getCo() == this.role)
				.collect(Collectors.toList());
	}

	void updateSizes() {
		size = this.playerList.size();
		trueSize = sr.getRoleSizeMap().get(this.role);

		hasWws = size - trueSize;
	}

	void updateTruePer() {
		if (playerList == null) {
			return;
		}

		if (trueSize > size) {
			return;
			//あとで実装
			//replaceAllで他を0にしてlatentListと一緒にVillsPerを算出してtruePerに入れる
		}

		float truePer = trueSize / size;
		truePer = (float)Math.ceil( truePer * 1000) / 1000;

		for (Player player : playerList) {
			player.getTruePerMap().put(this.role, truePer);
			player.getTruePerMap().replaceAll((key, value) -> key.equals(this.role) ? value : 0);
			player.setVillsPer(truePer);
			player.setWwsPer(truePer);
		}
	}

	void updateAliveWws() {
		float temp = playerList.stream()
				.filter(p -> !p.isAlive())
				.map(p -> 1 - p.getVillsPer())
				.reduce(0.0f, (a, b) -> a + b);

		temp = (int)Math.floor(temp);
		confDeadWws = (int)temp;
	}

	public int size() {
		return playerList.size();
	}

	public List<Player> getList() {
		return playerList;
	}
	
	

	public int getConfDeadWws() {
		return confDeadWws;
	}

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}
}
