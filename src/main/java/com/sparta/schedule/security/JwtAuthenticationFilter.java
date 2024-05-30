package com.sparta.schedule.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule.dto.user.LoginRequest;
import com.sparta.schedule.UserRoleEnum;
import com.sparta.schedule.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "ERROR_FILE_LOGGER")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res
    ) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper().readValue(
                    req.getInputStream(), LoginRequest.class
            );

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult
    ) {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtProvider.createToken(username, role);
        res.addHeader(JwtProvider.AUTHORIZATION_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest req, HttpServletResponse res, AuthenticationException failed
    ) {
        res.setStatus(401);
    }

}