package com.br.assembly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
	
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
				.components(new Components())
				.info(new Info()
						.description("Projeto para gerenciar sessões de votações em pautas de assembleia.")
						.title("Assembly")
						.contact(new Contact().name("Assembly"))
						.version("1.0.0"));
	}

}
