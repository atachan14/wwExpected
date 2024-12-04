package model;

import java.util.List;
import java.util.stream.Collectors;

import model.role.person.Role;

public class Cog {
	static SessionRegulation sr;
	SessionBoard sb;
	Role role;
	List<Player> playerList;
	int size;
	int hasWws;

	public Cog(SessionBoard sb, Role role) {
		this.sb = sb;
		this.role = role;
		criatePlayerList();
		updateTruePer();

	}

	void criatePlayerList() {
		playerList = sb.getPlayerList().stream()
				.filter(a -> a.getCo() == this.role)
				.collect(Collectors.toList());
	}

	void updateTruePer() {
		if (playerList == null) {
			return;
		}

		if (sr.getRoleSizeMap().get(this.role) > playerList.size()) {
			return;
			//あとで実装
			//replaceAllで他を0にしてlatentListと一緒にVillsPerを算出、truePerに入れる
		}

		float truePer = sr.getRoleSizeMap().get(this.role) / playerList.size();
		for (Player player : playerList) {
			player.getTruePerMap().put(this.role, truePer);
			player.getTruePerMap().replaceAll((key, value) -> key.equals(this.role) ? value : 0);
			player.setVillsPer(truePer);
		}
	}

	public int size() {
		return playerList.size();
	}

	public List<Player> getList() {
		return playerList;
	}

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}
}
