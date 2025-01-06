package com.sasicodes.securecapita.service;

import com.sasicodes.securecapita.domain.User;
import com.sasicodes.securecapita.dto.UserDto;

public interface UserService {

    UserDto createUser(User user);

    UserDto getUserByEmail(String email);
}
