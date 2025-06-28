package com.eduducer.repositoryranking.adapter.outbound.github.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GitHubSimpleUser(
    long id,
    String name,
    String email,
    String login,
    String nodeId,
    String avatarUrl,
    String url,
    String followersUrl,
    String followingUrl,
    String gistsUrl,
    String starredUrl,
    String type
) {

}
