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
	List<Player> alivePlayerList = new ArrayList<Player>();

	List<SessionBoard> nextSbList = new ArrayList<SessionBoard>();

	public SessionBoard(Fase fase) {
		this.cp = new CalcPer(sr, this);
		this.fase = fase;
		criatePlayerList();
		criateCoPlayerListMap();
		criateLatentPlayerList();

		criateNextSbList();
		cp.updateVillsPer();
	}

	public SessionBoard() {

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

	void criateNextSbList() {

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

	public void setSr(SessionRegulation SR) {
		sr = SR;
	}

}
