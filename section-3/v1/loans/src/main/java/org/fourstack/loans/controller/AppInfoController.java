package org.fourstack.loans.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.fourstack.loans.config.AppInfoConfig;
import org.fourstack.loans.config.LoansInfo;
import org.fourstack.loans.dto.AppContactDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
@Tag(
        name = "REST API for fetching Application information"
)
public class AppInfoController {

    private final AppInfoConfig appInfoConfig;
    private final LoansInfo loansInfo;

    @Operation(
            summary = "API to get the application information",
            description = "REST API to fetch the application information"
    )
    @GetMapping("/info")
    public ResponseEntity<String> getApplicationInfo() {
        String appInfo = """
                {
                    "applicationName": "%s",
                    "author": "%s",
                    "description" : "%s",
                    "version" : "%s"
                }
                """.formatted(appInfoConfig.getName(), appInfoConfig.getAuthor(),
                appInfoConfig.getDescription(), appInfoConfig.getVersion());

        return ResponseEntity.ok(appInfo);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AppContactDetails> getLoanAppDetails() {
        AppContactDetails details = new AppContactDetails(loansInfo.getMessage(),
                loansInfo.getContactDetails(), loansInfo.getOnCallSupport());
        return ResponseEntity.ok(details);
    }
}
