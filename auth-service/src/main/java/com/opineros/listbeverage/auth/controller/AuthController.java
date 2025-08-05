package com.opineros.listbeverage.auth.controller;

import com.opineros.listbeverage.auth.model.User;
import com.opineros.listbeverage.auth.service.UserService;
import com.opineros.listbeverage.auth.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authManager;

    public AuthController(UserService userService, JwtTokenProvider tokenProvider, AuthenticationManager authManager) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        User user = userService.register(username, password);
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.loadUser(username);
            String token = tokenProvider.createToken(username, user.getRole());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
