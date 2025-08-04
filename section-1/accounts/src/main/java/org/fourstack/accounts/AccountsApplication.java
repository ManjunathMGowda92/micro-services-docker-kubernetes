package org.fourstack.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts-Service : REST API Documentation",
				description = "REST-API Documentation for Accounts-Service microservice.",
				version = "1.0",
				contact = @Contact(
						name = "Manjunath HM",
						email = "fourstackdevelopers@gmail.com",
						url = "https://fourstack-dev.in"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://fourstack-dev.in"
				)
		),
		externalDocs = @ExternalDocumentation(
				url = "https://fourstack-dev.in/accounts-service",
				description = "REST-API Documentation for Accounts-Service microservice"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
