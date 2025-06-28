package com.eduducer.repositoryranking.adapter.outbound.github;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GitHubSearchQueryBuilder {

  public String buildSearchQuery(final String language, final LocalDate createdAfter) {
    if (!StringUtils.hasText(language)) {
      throw new IllegalArgumentException("Language must not be blank!");
    }
    final var query = new StringBuilder("language:").append(language);
    if (createdAfter != null) {
      query.append(" created:>").append(createdAfter);
    }
    return query.toString();
  }
}
