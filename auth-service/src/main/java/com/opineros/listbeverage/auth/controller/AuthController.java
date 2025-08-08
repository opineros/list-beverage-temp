package com.opineros.listbeverage.auth.controller;

import com.opineros.listbeverage.auth.model.User;
import com.opineros.listbeverage.auth.service.UserService;
import com.opineros.listbeverage.auth.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.ErrorResponse;
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
    @Operation(summary = "Create a user")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content),
            @ApiResponse(responseCode = "404", description = "The user could not be saved", content =
            @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        User user = userService.register(username, password);
        return ResponseEntity.ok(Map.of("id", user.getId(), "username", user.getUsername()));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content),
            @ApiResponse(responseCode = "404", description = "The user could not authenticate", content =
            @Content(schema = @Schema(implementation = ErrorResponse.class)))})
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
