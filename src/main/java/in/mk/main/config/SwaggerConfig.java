package in.mk.main.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("Enotes API")
                .description("Enotes API")
                .version("1.0.0")
                .termsOfService("https://thebecoder.com")
                .contact(new Contact()
                        .name("Mukesh Labana")
                        .email("thebecoder@gmail.com")
                        .url("https://thebecoder.com/contact"))
                .license(new License()
                        .name("Enotes 1.0")
                        .url("http://thebecoder.com"));

        List<Server> serverList = List.of(
                new Server().description("Dev").url("http://localhost:8080"),
                new Server().description("Test").url("http://localhost:8081"),
                new Server().description("Prod").url("http://localhost:8082")
        );

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(In.HEADER);

        Components components = new Components()
                .addSecuritySchemes("Token", securityScheme);

        return new OpenAPI()
                .info(info)
                .servers(serverList)
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList("Token"));
    }
}