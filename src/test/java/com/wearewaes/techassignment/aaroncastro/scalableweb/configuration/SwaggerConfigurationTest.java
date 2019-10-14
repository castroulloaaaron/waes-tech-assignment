package com.wearewaes.techassignment.aaroncastro.scalableweb.configuration;

import org.junit.Test;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SwaggerConfigurationTest {

    @Test
    public void createSwaggerConfiguration() {
        final Docket docket = new SwaggerConfiguration().swaggerApiDocumentation();

        assertNotNull(docket, "Swagger Docket configuration object should not be null");
    }
}
