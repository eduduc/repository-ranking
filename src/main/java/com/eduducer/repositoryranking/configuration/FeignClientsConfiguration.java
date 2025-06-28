package com.eduducer.repositoryranking.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.eduducer.repositoryranking.adapter.outbound")
public class FeignClientsConfiguration {

}
