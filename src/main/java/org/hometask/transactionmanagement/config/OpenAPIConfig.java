package org.hometask.transactionmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("bo.kongliu@gmail.com");
        contact.setName("Bo");

        Info info = new Info()
                .title("Transaction Management API")
                .version("1.0")
                .description("This API provides endpoints to manage transactions")
                .contact(contact);

        return new OpenAPI().info(info);
    }
}