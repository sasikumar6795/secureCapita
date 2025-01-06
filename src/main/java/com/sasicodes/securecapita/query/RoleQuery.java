package com.sasicodes.securecapita.query;

public class RoleQuery {

    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * from Roles WHERE name = :roleName";
    public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT INTO UserRoles (users_user_id, roles_role_id) VALUES (:usersUserId, :rolesRoleId)";
    public static final String SELECT_ROLE_BY_ID = "SELECT r.role_id, r.name, r.permission from Roles r JOIN UserRoles ur ON ur.id = r.role_id JOIN" +
            " USERS u ON u.user_id = ur.user_id  WHERE ur.users_user_id = :user_id";
}
