package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.logic.CalcPer;
import model.role.child.Latent;
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
		criateCogMap();

		countWws();
		checkEnd();

		if (winner == "--") {
			criateNextFase();
			criateParallelFbMap();
			calcExedWinPer();
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
			System.out.println("nextFase error→" + this.fase);
			break;
		}
	}

	void criateParallelFbMap() {
		for (Player p : alivePlayerList) {
			for (Role role : p.getTruePerMap().keySet()) {
				float nextBoardPer = p.getTruePerMap().get(role);
				FaseBoard nextFb = new FaseBoard(nextFase, nextBoardPer, p.getId(), role, this);
				p.getParallelFbMap().put(role, nextFb);
			}
		}
	}

	void calcExedWinPer() {
		for (Player player : playerList) {
			float winPer = 0;
			for (Role role : player.getParallelFbMap().keySet()) {
				winPer += player.getParallelFbMap().get(role).getVillsWinPer()
						* player.getParallelFbMap().get(role).getBoardPer();
			}
			player.setExedVillsWinPer(winPer);
		}
	}

	void criateCopyedPlayerList(FaseBoard beforBoard) {
		playerList = beforBoard.getPlayerList().stream()
				.map(p -> new Player(p.getId(), p.getName(), p.getCo(), p.isAlive()))
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



	void criatePlayerList() {
		Latent latent = (Latent) sr.getCanCoList().stream()
				.filter(a -> a.getName() == "？")
				.findAny()
				.get();
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, latent, sr.getRoleSizeMap()));
		}
	}

	void criateCogMap() {
		Map<Role, List<Player>> coPlayerListMap = playerList.stream()
				.collect(Collectors.groupingBy(Player::getCo));

		for (Role co : coPlayerListMap.keySet()) {
			if (co.getName() == "？") {
				latentg = new Latentg(this, co, coPlayerListMap.get(co));
			} else {
				Cog cog = new Cog(this, co, coPlayerListMap.get(co));
				cogMap.put(co, cog);
				cogList.add(cog);
			}
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
