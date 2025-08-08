package com.opineros.listbeverage.auth.config;

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
                title = "Auth Service API",
                version = "v1.0",
                description = "API de autenticación de List Beverage",
                contact = @Contact(name = "Equipo Auth Service", email = "soporte@listbeverage.com"),
                license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
        ),
        servers = @Server(url = "/")
)
@Configuration
public class SwaggerConfig {

    /**
     * Agrupa los endpoints de autenticación bajo el grupo "auth"
     * y documenta únicamente las rutas que coincidan con /api/auth/**
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/auth/**")
                .build();
    }
}