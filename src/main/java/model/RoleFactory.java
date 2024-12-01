package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import model.role.child.Villager;
import model.role.child.Wolf;
import model.role.person.Role;

public class RoleFactory {
    private static final Map<String, Supplier<Role>> appRoleMap = new LinkedHashMap<>();

    static {
        appRoleMap.put("素村", Villager::new);
        appRoleMap.put("狼", Wolf::new);
    }

    public static Role createRole(String roleName) {
        Supplier<Role> roleSupplier = appRoleMap.get(roleName);
        if (roleSupplier == null) {
            throw new IllegalArgumentException("Unknown role: " + roleName);
        }
        return roleSupplier.get();
    }

	public static Map<String, Supplier<Role>> getAppRoleMap() {
		return appRoleMap;
	}
    
}

