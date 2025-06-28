package com.eduducer.repositoryranking.adapter.outbound.github.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GitHubRepositorySearchResponse(
    int totalCount,
    boolean incompleteResults,
    List<GitHubRepositoryItem> items
) {
}
