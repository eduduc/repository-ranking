package com.eduducer.repositoryranking.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI repositoryRankingApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Repository Ranking Service API")
            .version("v1.0.0"));
  }
}
