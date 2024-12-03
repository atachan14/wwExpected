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

	float villsPer;
	Map<Role, Float> rolePerMap = new LinkedHashMap<>();
	float wwsPer;
	float wPer;
	float kPer;

	float villsWinPer;
	float wwsWinPer;

	public Player(int i, Map<Role, Integer> roleMap) {
		num = i + 1;
		co = new Latent();
		for (Role canCo : roleMap.keySet()) {
			rolePerMap.put(canCo, 0f);
		}
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

	public float getVillsPer() {
		return villsPer;
	}

	public void setVillsPer(float villsPer) {
		this.villsPer = villsPer;
	}

	public Map<Role, Float> getRolePerMap() {
		return rolePerMap;
	}

	public void setRolePerMap(Map<Role, Float> rolePerMap) {
		this.rolePerMap = rolePerMap;
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