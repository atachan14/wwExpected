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

	boolean villsWin = false;
	boolean wwsWin = false;
	int maxWws;

	List<Player> playerList = new ArrayList<Player>();

	Map<Role, Cog> cogMap = new LinkedHashMap<>();
	List<Player> latentPlayerList = new ArrayList<Player>();
	List<Player> alivePlayerList;
	int aliveWws;

	List<FaseBoard> nextFbList;

	public FaseBoard() {

	}

	public FaseBoard(Fase fase) {
		this.fase = fase;
		this.cp = new CalcPer(this);
		
		criatePlayerList();
		criateCogMap();
		countWws();
		criateLatentPlayerList();

		cp.updateVillsPer();
		//		checkEnd();

		//		if (!villsWin && !wwsWin) {
		//			criateNextSbList();
		//		}
	}
	
	
	void countWws() {
		maxWws = sr.getWwsList().size();
		aliveWws = maxWws;
		
		for(Role role : cogMap.keySet()) {
			aliveWws -= cogMap.get(role).getConfDeadWws();
		}
		
	}

	public FaseBoard(Fase fase, Player exedPlayer, Fase beforFase) {
		this.fase = fase;

	}

	void criateNextSbList() {
		switch (this.fase.getZone()) {
		case "d":
			Fase nextFase = new Fase("n", fase.getDay());
			for (Player p : alivePlayerList) {
				nextFbList.add(new FaseBoard(nextFase, p, fase));
			}
		}
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

	}

	void criatePlayerList() {
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, sr.getRoleSizeMap()));
		}
	}

	void criateCogMap() {
		for (Role canCo : sr.getRoleSizeMap().keySet()) {
			cogMap.put(canCo, new Cog(this, canCo));
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

	public List<Player> getLatentPlayerList() {
		return latentPlayerList;
	}

	public static void setSr(SessionRegulation SR) {
		sr = SR;
	}

}
