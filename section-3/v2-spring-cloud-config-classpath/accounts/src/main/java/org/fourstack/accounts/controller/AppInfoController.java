package org.fourstack.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/info")
@Tag(
        name = "REST API for fetching Application information"
)
public class AppInfoController {

    @Value("${app.details.name}")
    private String applicationName;

    @Value("${app.details.author}")
    private String author;

    @Value("${app.details.description}")
    private String description;

    @Value("${app.details.version}")
    private String version;

    @Value("${app.details.environment}")
    private String environmentName;

    @Operation(
            summary = "API to get the application information",
            description = "REST API to fetch the application information"
    )
    @GetMapping
    public ResponseEntity<String> getApplicationInfo() {
        String appInfo = """
                {
                    "applicationName": "%s",
                    "author": "%s",
                    "description" : "%s",
                    "version" : "%s",
                    "environment" : "%s"
                }
                """.formatted(applicationName, author, description, version, environmentName);

        return ResponseEntity.ok(appInfo);
    }
}
