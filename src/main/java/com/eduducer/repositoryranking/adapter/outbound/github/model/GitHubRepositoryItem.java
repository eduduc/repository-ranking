package com.eduducer.repositoryranking.adapter.outbound.github.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.OffsetDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GitHubRepositoryItem(
    long id,
    String name,
    String fullName,
    String description,
    GitHubSimpleUser owner,
    String url,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime pushedAt,
    int size,
    int stargazersCount,
    int watchersCount,
    String language,
    double score,
    int forks,
    int openIssues,
    int watchers
    ) { }
