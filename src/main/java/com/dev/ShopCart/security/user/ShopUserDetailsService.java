package com.dev.ShopCart.security.user;

import com.dev.ShopCart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.dev.ShopCart.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not Found")
        );
        return ShopUserDetails.buildUserDetails(user);
    }
}
