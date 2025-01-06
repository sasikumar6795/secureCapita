package com.sasicodes.securecapita.implementation;

import com.sasicodes.securecapita.domain.Role;
import com.sasicodes.securecapita.domain.User;
import com.sasicodes.securecapita.domain.UserPrincipal;
import com.sasicodes.securecapita.exception.ApiException;
import com.sasicodes.securecapita.repository.RoleRepository;
import com.sasicodes.securecapita.repository.UserRepository;
import com.sasicodes.securecapita.rowMapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.sasicodes.securecapita.enumeration.RoleType.ROLE_USER;
import static com.sasicodes.securecapita.enumeration.VerificationType.ACCOUNT;
import static com.sasicodes.securecapita.query.UserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User>, UserDetailsService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RoleRepository<Role> roleRepository;

    private final PasswordEncoder bCryptPasswordEncoder;
    @Override
    public User create(User user) {
        // check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0 ) {
            throw new ApiException("Email already in user. Please use a different email id");
        }
        
        // save new user
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = getSqlParameterSource(user);
            jdbcTemplate.update(INSERT_USER_QUERY, parameterSource, holder);
            user.setUser_id(Objects.requireNonNull(holder.getKey()).longValue());
            // add role to the user
            roleRepository.addRoleToUser(user.getUser_id(), ROLE_USER.name());

            // send verification URL
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            // save URL in verification table
            jdbcTemplate.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("usersUserId",user.getUser_id(), "url",verificationUrl));
            // send email to user with verification URL

            user.setEnabled(false);
            user.setNotLocked(true);
            // return the newly created user
            return user;
            // if any errors, throw exception with proper message
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong please try again !!!!");
        }
    }

    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public User getData(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private int getEmailCount(String email) {
        return jdbcTemplate.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email",email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", bCryptPasswordEncoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/" + type +"/" + key).toUriString();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
        if(user == null) {
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in database: {}", email);
            return new UserPrincipal(user, roleRepository.getRoleByUserId(user.getUser_id()).getPermission());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL_QUERY, Map.of("email", email), new UserRowMapper());
            return user;
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No User found by email: " + email);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong please try again !!!!");
        }
    }
}
