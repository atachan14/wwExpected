package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.logic.CalcPer;
import model.role.person.Role;

public class FaseBoard implements Serializable {
	static SessionRegulation sr;
	CalcPer cp;
	Fase fase;

	String winner = "--";
	boolean villsWin = false;
	boolean wwsWin = false;
	float villsWinPer;
	float wwsWinPer;

	float boardPer;

	List<Player> playerList = new ArrayList<Player>();

	Map<Role, Cog> cogMap = new LinkedHashMap<>();
	List<Cog> cogList = new ArrayList<>();
	Latentg latentg;
	List<Player> latentPlayerList = new ArrayList<Player>();

	List<Player> alivePlayerList;
	int confDeadWwsSize = 0;
	int confAliveWwsSize = 0;

	Fase nextFase;
	Player exedPlayer;

	public FaseBoard() {

	}

	public FaseBoard(Fase fase) {
		this.fase = fase;
		this.cp = new CalcPer(this);

		this.boardPer = 1.0f;
		criatePlayerList();

		setup();

	}

	//	nextFase, boardPer,p.getNum(),role, this
	public FaseBoard(Fase fase, float boardPer, int num, Role role, FaseBoard beforBoard) {
		this.fase = fase;
		this.cp = new CalcPer(this);

		this.boardPer = boardPer;
		criateCopyedPlayerList(beforBoard);
		exeExedPlayer(num, role);

		setup();
	}

	void setup() {
		criateAlivePlayerList();
		List<String> a = playerList.stream()
				.map(b -> b.getCo().getName())
				.collect(Collectors.toList());
		System.out.println(a);

		criateCogMap();

		countWws();
		checkEnd();

		if (winner == "--") {
			criateNextFase();
			criateParallelFbMap();
			calcExedWinPer();
		}
	}

	void calcExedWinPer() {
		for (Player player : playerList) {
			float winPer = 0;
			for (Role role : player.getParallelFbMap().keySet()) {
				winPer += player.getParallelFbMap().get(role).getVillsWinPer()
						* player.getParallelFbMap().get(role).getBoardPer();
			}
			player.setExedWinPer(winPer);
		}
	}

	void criateCopyedPlayerList(FaseBoard beforBoard) {
		playerList = beforBoard.getPlayerList().stream()
				.map(p -> new Player(p.getNum(), p.getName(), p.getCo(), p.isAlive()))
				.collect(Collectors.toList());
	}

	void exeExedPlayer(int num, Role role) {
		this.exedPlayer = playerList.get(num - 1);
		exedPlayer.setAlive(false);
		exedPlayer.setConfRole(role);
	}

	void countWws() {
		for (Role role : cogMap.keySet()) {
			confDeadWwsSize += cogMap.get(role).getConfDeadWws();
		}

		for (Role role : cogMap.keySet()) {
			confAliveWwsSize += cogMap.get(role).getConfAliveWws();
		}
	}

	void checkEnd() {

		if (confDeadWwsSize == sr.getWwsList().size() && alivePlayerList.size() >= 1) {
			villsWin = true;
			villsWinPer = 1.0f;
			wwsWinPer = 0f;
		}

		if (alivePlayerList.size() - confAliveWwsSize <= confAliveWwsSize) {
			wwsWin = true;
			villsWinPer = 0f;
			wwsWinPer = 1.0f;
		}

		if (villsWin && !wwsWin) {
			winner = "村WIN";
		}

		if (!villsWin && wwsWin) {
			winner = "人狼WIN";
		}

		if (villsWin && wwsWin) {
			winner = "DRAW";
		}
	}

	void criateNextFase() {
		switch (this.fase.getZone()) {
		case "d":
			nextFase = new Fase("n", fase.getDay());
			break;

		case "n":
			nextFase = new Fase("d", fase.getDay() + 1);
			break;
		default:
			nextFase = new Fase("?", 0);
		}
	}

	void criateParallelFbMap() {
		for (Player p : alivePlayerList) {
			for (Role role : p.getTruePerMap().keySet()) {
				float nextBoardPer = p.getTruePerMap().get(role);
				p.getParallelFbMap().put(role, new FaseBoard(nextFase, nextBoardPer, p.getNum(), role, this));
			}
		}
	}

	void criatePlayerList() {
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, sr.getRoleSizeMap()));
		}
	}

	void criateCogMap() {

		for (Role canCo : sr.getCanCoList()) {
			if (canCo.getName() == "？") {
				latentg = new Latentg(this, canCo);
				continue;
			}
			Cog cog = new Cog(this, canCo);
			cogMap.put(canCo, cog);
			cogList.add(cog);
		}

	}

	void criateAlivePlayerList() {
		alivePlayerList = playerList.stream()
				.filter(p -> p.isAlive())
				.collect(Collectors.toList());
	}

	public CalcPer getCp() {
		return cp;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Map<Role, Cog> getCogMap() {
		return cogMap;
	}

	public List<Cog> getCogList() {
		return cogList;
	}

	public List<Player> getLatentPlayerList() {
		return latentPlayerList;
	}

	public String getWinner() {
		return winner;
	}

	public Fase getFase() {
		return fase;
	}

	public float getBoardPer() {
		return boardPer;
	}

	public List<Player> getAlivePlayerList() {
		return alivePlayerList;
	}

	public int getConfDeadWwsSize() {
		return confDeadWwsSize;
	}

	public int getConfAliveWwsSize() {
		return confAliveWwsSize;
	}

	public Player getExedPlayer() {
		return exedPlayer;
	}

	public float getVillsWinPer() {
		return villsWinPer;
	}

	public float getWwsWinPer() {
		return wwsWinPer;
	}

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}

}
