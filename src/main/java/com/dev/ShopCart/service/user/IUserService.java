package com.dev.ShopCart.service.user;

import com.dev.ShopCart.dto.UserDto;
import com.dev.ShopCart.request.CreateUserRequest;
import com.dev.ShopCart.request.UserUpdateRequest;

public interface IUserService {
    UserDto getUserById(Long userId);
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}
