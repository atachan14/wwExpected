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
	float winPer;
	int maxWws;

	float boardPer;
	float boardWinPer;
	float maxWinPer;

	List<Player> playerList = new ArrayList<Player>();

	Map<Role, Cog> cogMap = new LinkedHashMap<>();
	List<Cog> cogList = new ArrayList<>();
	List<Player> latentPlayerList = new ArrayList<Player>();

	List<Player> alivePlayerList;
	int confDeadWwsSize = 0;
	int confAliveWwsSize = 0;

	Fase nextFase;
	List<FaseBoard> nextFbList;
	Player exedPlayer;

	public FaseBoard() {

	}

	public FaseBoard(Fase fase) {
		this.fase = fase;
		this.cp = new CalcPer(this);

		this.boardPer = 1.0f;
		criatePlayerList();
		criateCogMap();
		countWws();
		criateLatentPlayerList();

		cp.updateVillsPer();
		checkEnd();

		if (winner == "--") {
			criateNextFase();
			criateNextSbList();
		}
	}

	//	nextFase, boardPer,p.getNum(),role, this
	public FaseBoard(Fase fase, float boardPer, int num, Role role, FaseBoard beforBoard) {
		this.fase = fase;
		this.cp = new CalcPer(this);

		this.boardPer = boardPer;
		criateCopyedPlayerList(beforBoard);
		exeExedPlayer(num, role);

		criateCogMap();
		countWws();
		criateLatentPlayerList();

		//		cp.updateVillsPer();
		checkEnd();

		if (winner == "--") {
			criateNextFase();
			criateNextSbList();
			calcExeWinPer();
		}
	}

	void calcExeWinPer() {

	}

	void criateCopyedPlayerList(FaseBoard beforBoard) {
		playerList = beforBoard.getPlayerList().stream()
				.map(p -> new Player(p.getNum(), p.getName(), p.getCo(), p.isAlive()))
				.collect(Collectors.toList());
	}

	void exeExedPlayer(int num, Role role) {
		this.exedPlayer = playerList.get(num - 1);
		exedPlayer.setAlive(false);
		exedPlayer.setTempRole(role);
	}

	void countWws() {
		for (Role role : cogMap.keySet()) {
			confDeadWwsSize += cogMap.get(role).getConfDeadWws();
		}

		for (Role role : cogMap.keySet()) {
			confAliveWwsSize += cogMap.get(role).getConfAliveWws();
		}

		alivePlayerList = playerList.stream()
				.filter(a -> a.isAlive())
				.collect(Collectors.toList());

		System.out.println(confDeadWwsSize);

	}

	void checkEnd() {

		if (confDeadWwsSize == sr.getWwsList().size() && alivePlayerList.size() >= 1) {
			villsWin = true;
		}

		if (alivePlayerList.size() - confAliveWwsSize <= confAliveWwsSize) {
			wwsWin = true;
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

	void criateNextSbList() {
		for (Player p : alivePlayerList) {
			for (Role role : p.getCampPerMap().keySet()) {
				float boardPer = p.getCampPerMap().get(role);
				nextFbList.add(new FaseBoard(nextFase, boardPer, p.getNum(), role, this));
			}
		}
	}

	void setup() {

	}

	void criatePlayerList() {
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, sr.getRoleSizeMap()));
		}
	}

	void criateCogMap() {
		for (Role canCo : sr.getRoleSizeMap().keySet()) {
			Cog cog = new Cog(this, canCo);
			cogMap.put(canCo, cog);
			cogList.add(cog);
		}
	}

	void criateLatentPlayerList() {
		for (Player player : playerList) {
			latentPlayerList.add(player);
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

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}

}
