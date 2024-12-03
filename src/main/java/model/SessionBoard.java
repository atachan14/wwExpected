package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import model.logic.CalcPer;
import model.role.person.Role;

public class SessionBoard {
	SessionRegulation sr;
	CalcPer cp;
	
	List<Player> playerList = new ArrayList<Player>();
	Map<Role, List<Player>> coPlayerMap = new LinkedHashMap<>();
	List<Player> notCoPlayerList = new ArrayList<Player>();
	
	List<SessionBoard> nextSbList = new ArrayList<SessionBoard>();

	public SessionBoard(HttpServletRequest request) {
		this.sr = (SessionRegulation) request.getSession().getAttribute("sr");
		this.cp = new CalcPer(sr,this);
		criatePlayerList();
		criateCoPlayerMap();
		criateNotCoPlayerList();
		cp.updateVillsPer();
	}

	void criatePlayerList() {
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, sr.getRoleSizeMap()));
		}
	}
	
	void criateCoPlayerMap() {
		for (Role canCo : sr.roleSizeMap.keySet()) {
			coPlayerMap.put(canCo, new ArrayList<>());
		}
	}
	
	void criateNotCoPlayerList() {
		for(Player player : playerList) {
			notCoPlayerList.add(player);
		}
	}

	void updateCoPlayerMap() {
		coPlayerMap = playerList.stream().collect(Collectors.groupingBy(Player::getCo));
	}

	
	
	public CalcPer getCp() {
		return cp;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Map<Role, List<Player>> getCoPlayerMap() {
		return coPlayerMap;
	}

	public List<Player> getNotCoPlayerList() {
		return notCoPlayerList;
	}

}
