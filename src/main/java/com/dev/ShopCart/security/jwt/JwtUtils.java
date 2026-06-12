package com.dev.ShopCart.security.jwt;

import ch.qos.logback.classic.spi.IThrowableProxy;
import com.dev.ShopCart.constant.ApplicationConstants;
import com.dev.ShopCart.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtils {
     private final Environment env;
    public String generateTokenForUser(Authentication authentication){
        ShopUserDetails userPrincipal=(ShopUserDetails) authentication.getPrincipal();
        List<String> roles=userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder().subject(userPrincipal.getEmail()).claim("id" ,userPrincipal.getId())
                .claim("roles",roles).issuedAt(new java.util.Date())
                .expiration(new java.util.Date((new java.util.Date()).getTime() + 24 * 60 * 60 * 1000)).signWith(getSigningKey()).compact();
    }
    public String userNameFromToken(String token){
         return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new JwtException(e.getMessage());

        }
    }

    private SecretKey getSigningKey() {
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

}
