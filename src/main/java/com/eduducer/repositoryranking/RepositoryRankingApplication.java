package com.eduducer.repositoryranking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RepositoryRankingApplication {

	public static void main(final String[] args) {
		SpringApplication.run(RepositoryRankingApplication.class, args);
	}

}
