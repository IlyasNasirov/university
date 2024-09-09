package com.example.university.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
/**
 * Configuration class for setting up OpenAPI documentation.
 *
 * <p>This class defines a bean for {@link OpenAPI} to configure the OpenAPI documentation
 * for the University application. It sets the server URL, API information, and contact details.
 */

@Configuration
public class OpenApiConfig {
    /**
     * Creates and configures an {@link OpenAPI} bean.
     *
     * <p>Configures the OpenAPI documentation with the following details:
     * <ul>
     *     <li>Server URL: http://localhost:8080</li>
     *     <li>API Title: University API</li>
     *     <li>API Description: API documentation for the University application.</li>
     *     <li>API Version: 1.0.0</li>
     *     <li>Contact Name: Ilyas Nasirov</li>
     *     <li>Contact Email: ilyasnasirov9@gmail.com</li>
     * </ul>
     *
     * @return the configured {@link OpenAPI} instance
     */
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
