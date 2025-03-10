package com.example.brokerservice.auth.service;

import com.example.brokerservice.auth.dto.AuthDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthService(AuthenticationManager authManager, SecurityContextRepository securityContextRepository) {
        this.authManager = authManager;
        this.securityContextRepository = securityContextRepository;
    }

    public String login(AuthDto authDto, HttpServletRequest request, HttpServletResponse response) {
        // Validate User credentials
        Authentication authentication = authManager.authenticate(
                UsernamePasswordAuthenticationToken
                        .unauthenticated(authDto.getUsername(), authDto.getPassword()));

        // Create a new context
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        // Update SecurityContextHolder and Strategy
        this.securityContextRepository.saveContext(context, request, response);

        return "logged in";
    }
}
