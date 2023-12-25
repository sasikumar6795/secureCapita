package com.sasicodes.securecapita.query;

public class RoleQuery {

    public static final String SELECT_ROLE_BY_NAME_QUERY = "SELECT * from Roles WHERE name = :roleName";
    public static final String INSERT_ROLE_TO_USER_QUERY = "INSERT INTO UserRoles (users_user_id, roles_role_id) VALUES (:usersUserId, :rolesRoleId)";
}
