package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.logic.CalcPer;
import model.role.person.Role;

public class SessionBoard {
	SessionRegulation sr;

	Fase fase;
	CalcPer cp;
	
	List<Player> playerList = new ArrayList<Player>();
	Map<String, List<Player>> coPlayerMap = new LinkedHashMap<>();
	List<Player> notCoPlayerList = new ArrayList<Player>();
	
	List<SessionBoard> nextSbList = new ArrayList<SessionBoard>();

	public SessionBoard(SessionRegulation sr,Fase fase) {
		this.sr = sr;
		this.fase = fase;
		this.cp = new CalcPer(sr,this);
		
		criatePlayerList();
		criateCoPlayerMap();
		
		cp.updateVillsPer();
	}

	void criatePlayerList() {
		for (int i = 0; i < sr.getRoleList().size(); i++) {
			playerList.add(new Player(i, sr.getRoleSizeMap()));
		}
	}
	
	void criateCoPlayerMap() {
		for (Role canCo : sr.roleSizeMap.keySet()) {
			coPlayerMap.put(canCo.getName(), new ArrayList<>());
		}
	}
	

	void updateCoPlayerMap() {
		coPlayerMap = playerList.stream()
				.collect(Collectors.groupingBy(a -> a.getCo()));
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public Map<String, List<Player>> getCoPlayerMap() {
		return coPlayerMap;
	}

	public List<Player> getNotCoPlayerList() {
		return notCoPlayerList;
	}

}
