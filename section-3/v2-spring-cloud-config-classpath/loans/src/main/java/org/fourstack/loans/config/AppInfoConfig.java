package org.fourstack.loans.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "app.details")
@Getter
@Setter
public class AppInfoConfig {
    private String name;
    private String author;
    private String description;
    private String version;
    private String environment;
}
