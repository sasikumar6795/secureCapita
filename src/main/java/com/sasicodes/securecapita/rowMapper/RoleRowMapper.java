package com.sasicodes.securecapita.rowMapper;

import com.sasicodes.securecapita.domain.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class RoleRowMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Role.builder()
                .roleId(rs.getLong("role_id"))
                .name(rs.getString("name"))
                .permission(rs.getString("permissions"))
                .build();
    }
}
