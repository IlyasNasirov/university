package com.example.university.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(
                        List.of(new Server().description("Local server").url("http://localhost:8080")
                        )
                )
                .info(new Info()
                        .title("University API")
                        .description("API documentation for the University application.")
                        .version("1.0.0")
                        .contact(new Contact().name("Ilyas Nasirov").email("ilyasnasirov9@gmail.com"))
                );
    }

}
