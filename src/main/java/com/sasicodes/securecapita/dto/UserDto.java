package com.sasicodes.securecapita.dto;

import java.time.LocalDateTime;

public record UserDto(
         Long user_id,
         String firstName,
         String lastName,
         String email,
         String address,
         String phone,
         String title,
         String bio,
         String imageUrl,
         boolean enabled,
         boolean isNotLocked,
         boolean isUsingMfa,
         LocalDateTime createdAt
) {
}
