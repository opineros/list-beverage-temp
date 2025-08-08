package com.opineros.listbeverage.user.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "v1.0",
                description = "API de usuarios de List Beverage",
                contact = @Contact(name = "Equipo Usuario Service", email = "soporte@listbeverage.com"),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        servers = @Server(url = "/")
)
@Configuration
public class SwaggerConfig {

    /**
     * Agrupa los endpoints de autenticación bajo el grupo "user"
     * y documenta únicamente las rutas que coincidan con /api/user/**
     */
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/user/**")
                .build();
    }
}