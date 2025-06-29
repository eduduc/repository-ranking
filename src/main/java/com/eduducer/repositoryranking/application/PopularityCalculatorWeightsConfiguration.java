package com.eduducer.repositoryranking.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "repository-ranking.popularity-weights")
public record PopularityCalculatorWeightsConfiguration (
    int stars,
    int forks,
    int updatesRecency
) {}
