package com.dev.ShopCart.service.user;

import com.dev.ShopCart.dto.UserDto;
import com.dev.ShopCart.entity.Cart;
import com.dev.ShopCart.entity.Role;
import com.dev.ShopCart.entity.User;
import com.dev.ShopCart.exceptions.AlreadyExistsException;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.CartMapper;
import com.dev.ShopCart.mapper.UserMapper;
import com.dev.ShopCart.repository.RoleRepository;
import com.dev.ShopCart.repository.UserRepository;
import com.dev.ShopCart.request.CreateUserRequest;
import com.dev.ShopCart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException(request.getEmail() + " already exists!");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role ROLE_USER not found."));

        user.setRoles(Set.of(userRole));
        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setUser(user);
        user.setCart(cart);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> { throw new ResourceNotFoundException("User not found!"); });
    }


}
