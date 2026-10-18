package com.example.spring_zavrsni_zadatak.controller;


import com.example.spring_zavrsni_zadatak.domain.RefreshToken;
import com.example.spring_zavrsni_zadatak.dto.AuthRequestDTO;
import com.example.spring_zavrsni_zadatak.dto.JwtResponseDTO;
import com.example.spring_zavrsni_zadatak.dto.RefreshTokenRequestDTO;
import com.example.spring_zavrsni_zadatak.service.JwtService;
import com.example.spring_zavrsni_zadatak.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {
    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    private RefreshTokenService refreshTokenService;

    @PostMapping("/api/v1/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        String username = authRequestDTO.getUsername();
        String password = authRequestDTO.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);

        if (!authentication.isAuthenticated()) throw new UsernameNotFoundException("invalid user request..!!");

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(username);
        return JwtResponseDTO.builder()
                .accessToken(jwtService.generateToken(username))
                .token(refreshToken.getToken())
                .build();

    }

    @PostMapping("/api/v1/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @PostMapping("/api/v1/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequestDTO request) {

        refreshTokenService.deleteByToken(request.getToken());

        return ResponseEntity.ok("Uspješno ste odjavljeni.");
    }
}