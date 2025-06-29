package com.eduducer.repositoryranking.adapter.outbound.github;

import com.eduducer.repositoryranking.adapter.outbound.github.mapper.GitHubRepositorySearchResponseMapper;
import com.eduducer.repositoryranking.application.GitHubSearchApiService;
import com.eduducer.repositoryranking.domain.RepositoriesPopularityResponse;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultGitHubSearchApiService implements GitHubSearchApiService {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultGitHubSearchApiService.class);

  private final GitHubSearchQueryBuilder gitHubSearchQueryBuilder;
  private final GitHubSearchApiClient gitHubSearchApiClient;
  private final GitHubRepositorySearchResponseMapper gitHubRepositorySearchResponseMapper;

  public DefaultGitHubSearchApiService(
      final GitHubSearchApiClient gitHubSearchApiClient,
      final GitHubSearchQueryBuilder gitHubSearchQueryBuilder,
      final GitHubRepositorySearchResponseMapper gitHubRepositorySearchResponseMapper
  ) {
    this.gitHubSearchApiClient = gitHubSearchApiClient;
    this.gitHubSearchQueryBuilder = gitHubSearchQueryBuilder;
    this.gitHubRepositorySearchResponseMapper = gitHubRepositorySearchResponseMapper;
  }

  @Override
  public RepositoriesPopularityResponse searchAndRatePopularity(final String language, final LocalDate createdAfter) {
    LOG.atInfo().log("Searching for repositories popularity for language: '{}', createdAfter: '{}'", language, createdAfter);
    final var searchQuery = gitHubSearchQueryBuilder.buildSearchQuery(language, createdAfter);
    return gitHubRepositorySearchResponseMapper.map(gitHubSearchApiClient.searchRepositories(searchQuery));
  }
}
