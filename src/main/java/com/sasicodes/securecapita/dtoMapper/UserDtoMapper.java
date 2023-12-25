package com.sasicodes.securecapita.dtoMapper;


import com.sasicodes.securecapita.domain.User;
import com.sasicodes.securecapita.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoMapper implements Function<User, UserDto>{
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getUser_id(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getTitle(),
                user.getBio(),
                user.getImageUrl(),
                user.isEnabled(),
                user.isNotLocked(),
                user.isUsingMfa(),
                user.getCreatedAt()
        );
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return user;
    }
}
