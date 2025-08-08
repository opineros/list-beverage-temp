package com.opineros.listbeverage.user.messaging;

import com.opineros.listbeverage.user.entity.User;
import com.opineros.listbeverage.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Configuration
public class UserCreatedListener {

    private final UserRepository userRepository;

    public UserCreatedListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Este Consumer se invoca por cada userId recibido en 'user.created'.
     */
    @Bean
    public Consumer<Long> userCreatedIn() {
        return userId -> {
            // Registra un nuevo usuario local con la relación al auth-service
            User u = new User();
            u.setUserId(userId);
            // Auditoría de creación
            u.setCreatedDate(LocalDateTime.now());
            // puedes rellenar otros campos por defecto o nulos
            userRepository.save(u);
        };
    }
}