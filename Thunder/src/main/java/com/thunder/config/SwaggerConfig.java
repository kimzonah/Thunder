package com.thunder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	  @Bean
	  public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Thunder")
	              .description("김선하 이지혜 팀입니다.")
	              .version("v0.0.1")
	              .license(new License().name("SSAFY").url("https://www.ssafy.com")));
	  }
}
