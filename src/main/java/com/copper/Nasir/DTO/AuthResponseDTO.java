package com.copper.Nasir.DTO;

import com.copper.Nasir.Entity.User;

public record AuthResponseDTO(
        String id,
        String username,
        String email,
        String token
) {
    public static AuthResponseDTO from(User user, String token) {
        return new AuthResponseDTO(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                token
        );
    }
}
