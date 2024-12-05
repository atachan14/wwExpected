package model;

import java.util.LinkedHashMap;
import java.util.Map;

import model.role.child.Latent;
import model.role.person.Role;

public class Player {
	String mainDisplay = "";

	int num;
	String name;
	Role co;
	boolean alive = true;
	
	Role tempRole;

	float villsPer;
	Map<Role, Float> truePerMap = new LinkedHashMap<>();
	float wwsPer;
	float wPer;
	float kPer;
	
	Map<Role,Float> campPerMap =new LinkedHashMap<>();

	float villsWinPer;
	float wwsWinPer;

	public Player(int i, Map<Role, Integer> roleMap) {
		num = i + 1;
		co = new Latent();
		for (Role canCo : roleMap.keySet()) {
			truePerMap.put(canCo, 0f);
		}
	}
	
	public Player(int num,String name,Role co,boolean alive) {
		this.num = num;
		this.name = name;
		this.co = co;
		this.alive = alive;
	}

	public String getMainDisplay() {
		return mainDisplay;
	}

	public void setMainDisplay(String mainDisplay) {
		this.mainDisplay = mainDisplay;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getCo() {
		return co;
	}

	public void setCo(Role co) {
		this.co = co;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public Role getTempRole() {
		return tempRole;
	}

	public void setTempRole(Role tempRole) {
		this.tempRole = tempRole;
		this.truePerMap.put(tempRole,1.0f);
		this.truePerMap.replaceAll((key, value) -> key.equals(tempRole) ? value : 0);
		
	}

	public float getVillsPer() {
		return villsPer;
	}

	public void setVillsPer(float villsPer) {
		this.villsPer = villsPer;
	}

	public Map<Role, Float> getTruePerMap() {
		return truePerMap;
	}

	public void setTruePerMap(Map<Role, Float> truePerMap) {
		this.truePerMap = truePerMap;
	}

	public Map<Role, Float> getCampPerMap() {
		return campPerMap;
	}

	public void setCampPerMap(Map<Role, Float> campPerMap) {
		this.campPerMap = campPerMap;
	}

	public float getWwsPer() {
		return wwsPer;
	}

	public void setWwsPer(float wwsPer) {
		this.wwsPer = wwsPer;
	}

	public float getwPer() {
		return wPer;
	}

	public void setwPer(float wPer) {
		this.wPer = wPer;
	}

	public float getkPer() {
		return kPer;
	}

	public void setkPer(float kPer) {
		this.kPer = kPer;
	}

	public float getVillsWinPer() {
		return villsWinPer;
	}

	public void setVillsWinPer(float villsWinPer) {
		this.villsWinPer = villsWinPer;
	}

	public float getWwsWinPer() {
		return wwsWinPer;
	}

	public void setWwsWinPer(float wwsWinPer) {
		this.wwsWinPer = wwsWinPer;
	}

}