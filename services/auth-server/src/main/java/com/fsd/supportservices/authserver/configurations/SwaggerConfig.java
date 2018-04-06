package com.fsd.supportservices.authserver.configurations;

// Swagger Configuration

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    // Enable swagger documentation
    @Configuration
    @EnableSwagger2
    @Profile("!prod")
    protected static class SwaggerConfigEnable {
        @Bean
        public Docket authServiceApi() {
            return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                    .paths(Predicates.and(ant("/oauth/**"), Predicates.not(ant("/oauth/error")))).build();

        }
    }

    // Disable swagger in Production
    @Configuration
    @EnableSwagger2
    @Profile("prod")
    protected static class SwaggerConfigDisable {
        @Bean
        public Docket authServiceApi() {
            return new Docket(DocumentationType.SWAGGER_2).select()
                    .apis(RequestHandlerSelectors.basePackage("com.marks.authservice")).paths(regex("/.*")).build()
                    .pathMapping("/");

        }
    }

}
