package org.fourstack.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.fourstack.loans.config.AppInfoConfig;
import org.fourstack.loans.config.LoansInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {AppInfoConfig.class, LoansInfo.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Loans Service :: REST API Documentation",
				description = "Loans microservice REST API Documentation",
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
				url = "https://fourstack-dev.in/loans-service",
				description = "REST-API Documentation for Loans-Service microservice"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
