package com.sasicodes.securecapita.service.implementation;

import com.sasicodes.securecapita.domain.User;
import com.sasicodes.securecapita.dto.UserDto;
import com.sasicodes.securecapita.dtoMapper.UserDtoMapper;
import com.sasicodes.securecapita.repository.UserRepository;
import com.sasicodes.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserserviceImpl implements UserService {

    private final UserRepository<User> userRepository;

    private final UserDtoMapper userDtoMapper;
    @Override
    public UserDto createUser(User user) {
        return userDtoMapper.apply(userRepository.create(user));
    }
}
