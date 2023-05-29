package com.malves.creditapplicationsystem.configuration

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Configuration

@Configuration
class Swagger3Config {

    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("springcreditapplicationsystem-public")
            .pathsToMatch("/api/customers/**", "/api/credits/**")
            .build()
    }
}