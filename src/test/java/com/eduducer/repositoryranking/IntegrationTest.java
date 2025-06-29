package com.eduducer.repositoryranking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Testcontainers
@SpringBootTest(
    classes = RepositoryRankingApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
public @interface IntegrationTest { }
