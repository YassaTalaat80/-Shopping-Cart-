package com.dev.ShopCart.controller;

import com.dev.ShopCart.dto.UserDto;
import com.dev.ShopCart.request.UserUpdateRequest;
import com.dev.ShopCart.security.user.ShopUserDetails;
import com.dev.ShopCart.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUserById(@AuthenticationPrincipal ShopUserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserById(userDetails.getId()));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserUpdateRequest request,
                                               @AuthenticationPrincipal ShopUserDetails userDetails) {
        return ResponseEntity.ok(userService.updateUser(request, userDetails.getId()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal ShopUserDetails userDetails) {
        userService.deleteUser(userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
