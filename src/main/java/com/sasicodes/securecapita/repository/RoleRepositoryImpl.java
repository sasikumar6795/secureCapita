package com.sasicodes.securecapita.repository;

import com.sasicodes.securecapita.domain.Role;
import com.sasicodes.securecapita.exception.ApiException;
import com.sasicodes.securecapita.rowMapper.RoleRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static com.sasicodes.securecapita.enumeration.RoleType.ROLE_USER;
import static com.sasicodes.securecapita.query.RoleQuery.*;


@Slf4j
@RequiredArgsConstructor
@Repository
public class RoleRepositoryImpl implements RoleRepository<Role> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RoleRowMapper roleRowMapper;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Role getData(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {

        log.info("Add role {} to userId: {}",roleName, userId);

        try {
            Role role = jdbcTemplate.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("roleName", roleName), roleRowMapper);
            jdbcTemplate.update(INSERT_ROLE_TO_USER_QUERY, Map.of("usersUserId", userId, "rolesRoleId", Objects.requireNonNull(role).getRoleId()));

        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.error(emptyResultDataAccessException.getMessage());
            throw new ApiException("No Role found by name: "+ROLE_USER.name());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An Error Occured. Please Try again");
        }

    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
