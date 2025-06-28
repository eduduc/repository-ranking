package com.eduducer.repositoryranking.domain;

public record RepositoryHostingUser(
    String id,
    String name,
    String url,
    String login,
    String email,
    String avatarUrl,
    String userType,
    String hostingPlatformName
) {

}
