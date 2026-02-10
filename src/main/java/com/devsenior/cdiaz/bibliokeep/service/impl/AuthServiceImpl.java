package com.devsenior.cdiaz.bibliokeep.service.impl;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.devsenior.cdiaz.bibliokeep.model.dto.LoginRequestDTO;
import com.devsenior.cdiaz.bibliokeep.model.dto.LoginResponseDTO;
import com.devsenior.cdiaz.bibliokeep.repository.UserRepository;
import com.devsenior.cdiaz.bibliokeep.service.AuthService;
import com.devsenior.cdiaz.bibliokeep.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public LoginResponseDTO login(LoginRequestDTO body) {
        try {
            var auth = new UsernamePasswordAuthenticationToken(body.email(), body.password());
            authenticationManager.authenticate(auth);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Credenciales inválidas");
        }

        var user = userRepository.findByEmail(body.email())
            .orElseThrow(() -> new RuntimeException("No existe el usuario con email: "+body.email()));
        var claims = Map.<String, Object>of(
            "user-id", user.getId(),
            "roles", user.getRoles().stream()
                            .map(r -> r.getName())
                            .toList()
        );

        var token = jwtService.generateToken(claims, body.email());

        return new LoginResponseDTO(token, "JWT");
    }
}
