package model;

import java.util.HashMap;
import java.util.Map;

import model.role.person.Role;

public class Player {
	String mainDisplay = "";

	int num;
	String name;
	Role co;
	
	boolean alive =true;

	float villsPer;
	Map<Role, Float> rolePerMap = new HashMap<>();
	float wwsPer;
	float wPer;
	float kPer;

	float villsWinPer;
	float wwsWinPer;

	public Player(int i, Map<Role, Integer> roleMap) {
		num = i + 1;
		for (Role canCo : roleMap.keySet()) {
			rolePerMap.put(canCo, 0f);
		}
	}

	public String getMainDisplay() {
		return mainDisplay;
	}

	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public Role getCo() {
		return co;
	}

	public float getVillsPer() {
		return villsPer;
	}

	public Map<Role, Float> getRolePerMap() {
		return rolePerMap;
	}

	public float getWwsPer() {
		return wwsPer;
	}

	public float getwPer() {
		return wPer;
	}

	public float getkPer() {
		return kPer;
	}

	public float getVillsWinPer() {
		return villsWinPer;
	}

	public float getWwsWinPer() {
		return wwsWinPer;
	}

	public void setMainDisplay(String mainDisplay) {
		this.mainDisplay = mainDisplay;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCo(Role co) {
		this.co = co;
	}

	public void setVillsPer(float villsPer) {
		this.villsPer = villsPer;
	}

	public void setRolePerMap(Map<Role, Float> rolePerMap) {
		this.rolePerMap = rolePerMap;
	}

	public void setWwsPer(float wwsPer) {
		this.wwsPer = wwsPer;
	}

	public void setwPer(float wPer) {
		this.wPer = wPer;
	}

	public void setkPer(float kPer) {
		this.kPer = kPer;
	}

	public void setVillsWinPer(float villsWinPer) {
		this.villsWinPer = villsWinPer;
	}

	public void setWwsWinPer(float wwsWinPer) {
		this.wwsWinPer = wwsWinPer;
	}

}
