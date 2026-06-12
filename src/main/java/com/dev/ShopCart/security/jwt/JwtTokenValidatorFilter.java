package com.dev.ShopCart.security.jwt;


import ch.qos.logback.core.util.StringUtil;
import com.dev.ShopCart.constant.ApplicationConstants;
import com.dev.ShopCart.security.user.ShopUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final ShopUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
      try {
          String jwt=parseJwt(request);
          if(StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)){
              String username = jwtUtils.userNameFromToken(jwt);
              UserDetails userDetails= userDetailsService.loadUserByUsername(username);
              var auth= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
              SecurityContextHolder.getContext().setAuthentication(auth);
          }
      }catch (JwtException e){
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write(e.getMessage()+"invalid or expired token ,try again");
          return;
      } catch (Exception e) {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          response.getWriter().write(e.getMessage());
          return;
      }
        filterChain.doFilter(request, response);;
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth=request.getHeader(ApplicationConstants.JWT_HEADER);
        if(StringUtils.hasText(headerAuth)&&headerAuth.startsWith(ApplicationConstants.JWT_PREFIX)){
            return headerAuth.substring(ApplicationConstants.JWT_PREFIX.length());
        }
        return null;
    }
}
