package com.infosys.internal.portal.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.infosys.internal.portal.claims" ,"com.infosys.internal.portal"})


@EnableJpaRepositories(basePackages =
		{"com.infosys.internal.portal.common.repository",
				"com.infosys.internal.portal.leave.management.repository",
		    "com.infosys.internal.portal.claims.repository"		
		
		})


@EntityScan(basePackages = {"com.infosys.internal.portal.common.model",
		"com.infosys.internal.portal.leave.management.models",  "com.infosys.internal.portal.claims.model"})

//@EnableConfigurationProperties({ FileStorageProperties.class })
public class InternalPortalApp {

	public static void main(String[] args) {
		SpringApplication.run(InternalPortalApp.class, args);
	}

}
