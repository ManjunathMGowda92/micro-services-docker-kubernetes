package org.fourstack.cards.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards/info")
@RequiredArgsConstructor
@Tag(
        name = "REST API for fetching Application information"
)
public class AppInfoController {

    @Autowired
    private final Environment environment;

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
                    "java-version" : "%s"
                }
                """.formatted(environment.getProperty("app.details.name"),
                environment.getProperty("app.details.author"),
                environment.getProperty("app.details.description"),
                environment.getProperty("app.details.version"),
                environment.getProperty("JAVA_HOME"));

        return ResponseEntity.ok(appInfo);
    }
}
