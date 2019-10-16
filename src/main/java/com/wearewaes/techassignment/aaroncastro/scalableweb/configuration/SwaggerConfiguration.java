package com.wearewaes.techassignment.aaroncastro.scalableweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Class that creates the configuration beans to expose the documentation using the Swagger utility/libraries.
 * @since version 1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    private final ApiInfo apiInfo;

    public SwaggerConfiguration() {
        apiInfo = new ApiInfo(
                "WAES Tech Assignment",
                "JSON Diff Base 64 API",
                "1.0.0",
                "Terms of service",
                new Contact("Aaron Castro", "https://www.linkedin.com/in/castroaaron", "castroulloa.aaron@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    /**
     * Creates a Docket bean that will expose the API REST documentation for the Actuator endpoint and the
     * DiffController REST Controller
     * @return Docket object
     */
    @Bean
    public Docket swaggerApiDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}
