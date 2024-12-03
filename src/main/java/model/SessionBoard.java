package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.logic.CalcPer;
import model.role.person.Role;

public class SessionBoard {
	static SessionRegulation sr;
	CalcPer cp;
	Fase fase;

	boolean villsWin = false;
	boolean wwsWin = false;

	List<Player> playerList = new ArrayList<Player>();
	Map<Role, List<Player>> coPlayerListMap = new LinkedHashMap<>();
	List<Player> latentPlayerList = new ArrayList<Player>();
	List<Player> alivePlayerList;
	int aliveWws;

	List<SessionBoard> nextSbList;

	public SessionBoard(Fase fase) {
		this.fase = fase;
		setup();

		cp.updateVillsPer();
		countAliveWws();
		checkEnd();

		if (!villsWin && !wwsWin) {
			criateNextSbList();
		}
	}

	public SessionBoard(Fase fase, Player exedPlayer, Fase beforFase) {
		this.fase = fase;
		setup();

	}

	void criateNextSbList() {
		switch (this.fase.getZone()) {
		case "d":
			Fase nextFase = new Fase("n", fase.getDay());
			for (Player p : alivePlayerList) {
				nextSbList.add(new SessionBoard(nextFase, p, fase));
			}
		}
	}

	void countAliveWws() {
		float temp = alivePlayerList.stream()
				.map(Player::getWwsPer)
				.reduce(0.0f, (a, b) -> a + b);
		aliveWws = Math.round(temp);

		System.out.println(fase + " aliveWws:" + aliveWws);
	}

	void checkEnd() {
		if (aliveWws == 0 && alivePlayerList.size() >= 1) {
			villsWin = true;
		}

		if (alivePlayerList.size() - aliveWws <= aliveWws) {
			wwsWin = true;
		}
	}

	void setup() {
		this.cp = new CalcPer(sr, this);
		criatePlayerList();
		criateCoPlayerListMap();
		criateLatentPlayerList();
	}

	void criatePlayerList() {
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, sr.getRoleSizeMap()));
		}
	}

	void criateCoPlayerListMap() {
		for (Role canCo : sr.roleSizeMap.keySet()) {
			coPlayerListMap.put(canCo, new ArrayList<>());
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

	void updateCoPlayerMap() {
		coPlayerListMap = playerList.stream().collect(Collectors.groupingBy(Player::getCo));
	}

	public CalcPer getCp() {
		return cp;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Map<Role, List<Player>> getCoPlayerMap() {
		return coPlayerListMap;
	}

	public List<Player> getNotCoPlayerList() {
		return latentPlayerList;
	}

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}

}
