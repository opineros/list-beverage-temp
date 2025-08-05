package com.opineros.listbeverage.auth.service;

import com.opineros.listbeverage.auth.model.User;
import com.opineros.listbeverage.auth.repository.RoleRepository;
import com.opineros.listbeverage.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import com.opineros.listbeverage.auth.model.Role;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password) {
        // Validar existencia de usuario o email
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }

        // Obtener rol por defecto (ROLE_USER)
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        // Construir entidad User
        User user = new User();
        user.setUsername(username); // username
        user.setPassword(passwordEncoder.encode(password)); // contraseña encriptada
        // Asignar rol por defecto
        user.setRole(defaultRole);
        // Auditoría de creación
        user.setCreatedDate(LocalDateTime.now());
        user.setLastModifiedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User loadUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
