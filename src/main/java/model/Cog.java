package model;

import java.util.List;
import java.util.stream.Collectors;

import model.role.person.Role;

public class Cog {
	SessionBoard sb;
	Role role;
	List<Player> playerList;
	int size;
	int hasWws;

	public Cog(SessionBoard sb, Role role) {
		this.sb = sb;
		this.role = role;
		criatePlayerList();

	}
	
	void criatePlayerList() {
		playerList = sb.getPlayerList().stream()
				.filter(a -> a.getCo() == this.role)
				.collect(Collectors.toList());
	}
	
	void updateTruePer() {
		
	}
	
	public int size() {
		return playerList.size();
	}
	
	public List<Player> getList(){
		return playerList;
	}
}
