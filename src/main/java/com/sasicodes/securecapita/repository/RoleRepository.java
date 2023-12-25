package com.sasicodes.securecapita.repository;

import com.sasicodes.securecapita.domain.Role;

import java.util.Collection;

public interface RoleRepository<T extends Role>{

    T create(T data);

    Collection<T> list(int page, int pageSize);

    T update(T data);

    T getData(Long id);

    Boolean delete(Long id);

    void addRoleToUser(Long userId, String roleName);

    Role getRoleByUserId(Long userId);

    Role getRoleByEmail(String email);

    void updateUserRole(Long userId, String roleName);


}
