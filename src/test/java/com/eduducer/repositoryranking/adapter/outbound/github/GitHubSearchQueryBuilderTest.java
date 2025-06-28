package com.eduducer.repositoryranking.adapter.outbound.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("GitHub Search Query Builder")
class GitHubSearchQueryBuilderTest {

  private final GitHubSearchQueryBuilder gitHubSearchQueryBuilder = new GitHubSearchQueryBuilder();

  @Test
  @DisplayName("builds a complete search query with 'language' and 'created' > 'createdAfter'")
  void buildsACompleteSearchQuery() {
    final var language = "ruby";
    final var createdAfter = LocalDate.of(2017, 6, 13); //"2017-06-13"

    assertEquals("language:ruby created:>2017-06-13", gitHubSearchQueryBuilder.buildSearchQuery(language, createdAfter));
  }

  @Test
  @DisplayName("builds a search query without 'createdAfter' parameter")
  void buildsASearchQueryWithoutCreatedAfterParameter() {
    final var language = "ruby";

    assertEquals("language:ruby", gitHubSearchQueryBuilder.buildSearchQuery(language, null));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("throws exception when no 'language' parameter is set")
  void buildsASearchQueryWithoutCreatedAfterParameter(final String language) {
    final var createdAfter = LocalDate.of(2017, 6, 13); //"2017-06-13"

    assertThrows(IllegalArgumentException.class, () -> gitHubSearchQueryBuilder.buildSearchQuery(language, createdAfter));
  }
}
