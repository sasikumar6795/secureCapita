package com.sasicodes.securecapita.repository;

import com.sasicodes.securecapita.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User>{

    T create(T data);

    Collection<T> list(int page, int pageSize);

    T update(T data);

    T getData(Long id);

    Boolean delete(Long id);


}
