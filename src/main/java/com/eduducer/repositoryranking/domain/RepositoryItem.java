package com.eduducer.repositoryranking.domain;

import java.time.OffsetDateTime;

public record RepositoryItem(
    String id,
    String fullName,
    String description,
    String language,
    RepositoryHostingUser owner,
    String url,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    OffsetDateTime pushedAt,
    int size,
    int stars,
    int watchersCount,
    int forks,
    int openIssues,
    Popularity calculatedPopularity
) {}
