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
	float vacantPer;
	int confDeadWws;
	int confAliveWws;

	public Cog(FaseBoard sb, Role role) {
		this.sb = sb;
		this.role = role;
		criatePlayerList();
		countSizes();
		checkIsFull();
		updateVillsPer();
		countConfDeadWws();

	}

	void criatePlayerList() {
		playerList = sb.getPlayerList().stream()
				.filter(a -> a.getCo() == this.role)
				.collect(Collectors.toList());
	}

	void countSizes() {
		size = this.playerList.size();
		trueSize = sr.getRoleSizeMap().get(this.role);
		if (size == 0)
			return;

		truePer = trueSize / size;
		hasWws = size - trueSize;
	}

	void checkIsFull() {
		if (trueSize <= size) {
			isFull = true;
		} else {
			isFull = false;
		}
	}

	void updateVillsPer() {
		if (!isFull) {
			return;
			//あとで実装
			//replaceAllで他を0にしてlatentListと一緒にVillsPerを算出してtruePerに入れる
		}

		for (Player player : playerList) {
			player.setVillsPer((float) Math.ceil(truePer * 10000) / 10000);
			player.setWwsPer((float) Math.ceil((1 - truePer) * 10000) / 10000);
			player.getTruePerMap().put(this.role, truePer);
			player.getTruePerMap().replaceAll((key, value) -> key.equals(this.role) ? value : 0);

		}
	}

	public void countConfDeadWws() {
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
