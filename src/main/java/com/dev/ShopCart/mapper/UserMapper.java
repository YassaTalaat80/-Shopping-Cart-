package com.dev.ShopCart.mapper;

import com.dev.ShopCart.dto.UserDto;
import com.dev.ShopCart.entity.Role;
import com.dev.ShopCart.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final CartMapper cartMapper;
    public  UserDto toDto(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(java.util.stream.Collectors.toSet());
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                Optional.ofNullable(user.getCart())
                        .map(cartMapper::toDto)
                        .orElse(null),
                roleNames
        );
    }
}
