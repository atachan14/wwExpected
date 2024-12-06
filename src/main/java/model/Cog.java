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
	int hasWws = 0;
	float truePer;

	boolean isFull;
	int confDeadWws;
	int confAliveWws;

	public Cog(FaseBoard sb, Role role, List<Player> playerList) {
		this.sb = sb;
		this.role = role;
		this.playerList = playerList;
		//		System.out.println(role.getName() + "groupのプレイヤーサイズ:" + playerList.size());
		countSizes();
		checkIsFull();
		updateTruePer();
		countConfWws();

	}

	void criatePlayerList() {
		playerList = sb.getPlayerList().stream()
				.filter(a -> a.getCo() == role)
				.collect(Collectors.toList());
	}

	void countSizes() {
		size = this.playerList.size();
		trueSize = sr.getRoleSizeMap().get(this.role);
		if (size == 0)
			return;

		truePer = (float) trueSize / size;
		hasWws = size - trueSize;
	}

	void checkIsFull() {
		if (trueSize <= size) {
			isFull = true;
		} else {
			isFull = false;
		}
	}

	void updateTruePer() {
		if (!isFull) {
			return;
			//あとで実装
			//replaceAllで他を0にしてlatentListと一緒にVillsPerを算出してtruePerに入れる？
		}

		for (Player player : playerList) {
			player.setVillsPer((float) Math.ceil(truePer * 10000) / 10000);
			player.setWwsPer((float) Math.ceil((1 - truePer) * 10000) / 10000);
			switch (this.role.getCamp()) {
			case "vills":
				player.getVillsTruePerMap().put(this.role, truePer);
				player.getVillsTruePerMap().replaceAll((key, value) -> key.equals(this.role) ? value : 0);
				break;
			case "wws":
				break;
			}
		}
	}

	public void countConfWws() {
		float temp = playerList.stream()
				.filter(p -> !p.isAlive())
				.map(p -> p.getWwsPer())
				.reduce(0.0f, (a, b) -> a + b);
		confDeadWws = (int) Math.floor(temp);

		temp = playerList.stream()
				.filter(p -> p.isAlive())
				.map(p -> p.getWwsPer())
				.reduce(0.0f, (a, b) -> a + b);
		confAliveWws = (int) Math.floor(temp);
	}

	public int size() {
		return playerList.size();
	}

	public List<Player> getList() {
		return playerList;
	}

	public boolean getIsFull() {
		return isFull;
	}

	public int getTrueSize() {
		return trueSize;
	}

	public int getConfDeadWws() {
		return confDeadWws;
	}

	public int getConfAliveWws() {
		return confAliveWws;
	}

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}
}
