package com.infosys.newsletter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@ComponentScan("com.infosys.newsletter")
public class SpringFoxConfig {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("com.infosys")).build().apiInfo(apiDetails());
	}

	public ApiInfo apiDetails() {
		return new ApiInfo("Newsletter API", "Simple API for Newsletter Module", "1.0", "Ready for use",
				new springfox.documentation.service.Contact("Gabriel Gheorghe", "infosys.com",
						"gabriel.gheorghe@infosysconsulting.com"),
				"API License", "https://infosys.com", Collections.emptyList());
	}

}
