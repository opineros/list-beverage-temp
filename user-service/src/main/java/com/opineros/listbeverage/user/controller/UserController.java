package com.opineros.listbeverage.user.controller;

import com.opineros.listbeverage.user.entity.User;
import com.opineros.listbeverage.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @GetMapping("/profile")
//    public ResponseEntity<Boolean> getProfile(Authentication auth) {
//        return ResponseEntity.ok(true);
//    }

    @GetMapping("/list")
    @Operation(summary = "View registered users")
    @ApiResponses(value  = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content),
            @ApiResponse(responseCode = "404", description = "Users cannot be retrieved", content =
            @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<List<User>> getAll() {
        List<User> users = (List<User>)  userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
