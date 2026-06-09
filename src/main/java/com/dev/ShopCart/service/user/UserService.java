package com.dev.ShopCart.service.user;

import com.dev.ShopCart.dto.UserDto;
import com.dev.ShopCart.entity.Cart;
import com.dev.ShopCart.entity.User;
import com.dev.ShopCart.exceptions.AlreadyExistsException;
import com.dev.ShopCart.exceptions.ResourceNotFoundException;
import com.dev.ShopCart.mapper.CartMapper;
import com.dev.ShopCart.repository.UserRepository;
import com.dev.ShopCart.request.CreateUserRequest;
import com.dev.ShopCart.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return toDto(user);
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
        user.setPassword(request.getPassword());

        Cart cart = new Cart();
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setUser(user);
        user.setCart(cart);

        return toDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserUpdateRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        return toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> { throw new ResourceNotFoundException("User not found!"); });
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                Optional.ofNullable(user.getCart())
                        .map(cartMapper::toDto)
                        .orElse(null));
    }
}
