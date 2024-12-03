package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import model.role.person.Role;

public class SessionRegulation {
	List<Role> roleList = new ArrayList<>();
	List<Role> villsList = new ArrayList<>();
	List<Role> wwsList = new ArrayList<>();
	//(市民,市民,市民,狼,狼...)

	Map<Role, Integer> roleSizeMap = new LinkedHashMap<>();
	Map<Role, Integer> villsRoleSizeMap = new LinkedHashMap<>();
	Map<Role, Integer> wwsRoleSizeMap = new LinkedHashMap<>();
	//(市民3,狼2...)

	String outRegulation = "";
	String outVills = "";
	String outWWs = "";

	public SessionRegulation(HttpServletRequest request) {
		SessionBoard.setSr(this);
		criateRoles(request);
		criateCamps();
	}

	void criateRoles(HttpServletRequest request) {
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			String key = entry.getKey();
			String value =entry.getValue()[0];
			if (value == null || value.isEmpty() || value.equals("0")) {
	            continue; // 空文字、null、または"0"をスキップ
	        }
			System.out.println(entry.getKey() + ":" + value);

			Role role = RoleFactory.createRole(key);
			int size = Integer.parseInt(entry.getValue()[0]);

			roleSizeMap.put(role, size);

			for (int i = 0; i < size; i++) {
				roleList.add(role);
			}
		}

		// debug用
		System.out.print("list:");
		for (Role role : roleList) {
			System.out.print("[" + role.getName() + "]");
		}

		System.out.println();
		System.out.print("map:");
		for (Map.Entry<Role, Integer> entry : roleSizeMap.entrySet()) {
			System.out.print("[" + entry.getKey().getName() + "*" + entry.getValue() + "]");
		}
	}

	void criateCamps() {
		for (Role role : roleList) {
			switch (role.getCamp()) {
			case "vills":
				villsList.add(role);
				break;
			case "wws":
				wwsList.add(role);
				break;
			}
		}

		outVills = "【村陣営:" + villsList.size() + "人】";
		outWWs = "【狼陣営:" + wwsList.size() + "人】";

		for (Map.Entry<Role, Integer> entry : roleSizeMap.entrySet()) {
			switch (entry.getKey().getCamp()) {
			case "vills":
				villsRoleSizeMap.put(entry.getKey(), entry.getValue());
				outVills += "[" + entry.getKey().getName() + entry.getValue() + "]";
				break;
			case "wws":
				wwsRoleSizeMap.put(entry.getKey(), entry.getValue());
				outWWs += "[" + entry.getKey().getName() + entry.getValue() + "]";
				break;
			}
		}
		outRegulation = outVills + " " + outWWs;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public List<Role> getVillsList() {
		return villsList;
	}

	public List<Role> getWwsList() {
		return wwsList;
	}

	public Map<Role, Integer> getRoleSizeMap() {
		return roleSizeMap;
	}

	public Map<Role, Integer> getVillsRoleSizeMap() {
		return villsRoleSizeMap;
	}

	public Map<Role, Integer> getWwsRoleSizeMap() {
		return wwsRoleSizeMap;
	}

	public String getOutRegulation() {
		return outRegulation;
	}

	public String getOutVills() {
		return outVills;
	}

	public String getOutWWs() {
		return outWWs;
	}

}
