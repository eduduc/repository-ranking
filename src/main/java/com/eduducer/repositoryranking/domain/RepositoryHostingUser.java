package com.eduducer.repositoryranking.domain;

public record RepositoryHostingUser(
    String id,
    String url,
    String login,
    String avatarUrl,
    String userType,
    String hostingPlatformName
) {

}
