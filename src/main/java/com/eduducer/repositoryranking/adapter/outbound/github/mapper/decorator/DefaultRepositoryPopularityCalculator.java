package com.eduducer.repositoryranking.adapter.outbound.github.mapper.decorator;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositoryItem;
import com.eduducer.repositoryranking.application.RepositoryPopularityCalculator;
import com.eduducer.repositoryranking.domain.Popularity;
import com.eduducer.repositoryranking.domain.Popularity.PopularityScale;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class DefaultRepositoryPopularityCalculator implements RepositoryPopularityCalculator<GitHubRepositoryItem, Popularity> {

  private static final PopularityScale POPULARITY_SCALE = PopularityScale.TOTAL_SCORE;

  @Override
  public Popularity calculatePopularity(final GitHubRepositoryItem calculable) {
    final var popularity = getPopularity(calculable);
    return new Popularity(String.valueOf(popularity), POPULARITY_SCALE);
  }

  private long getPopularity(final GitHubRepositoryItem calculable) {
    return calculable.stargazersCount() + calculable.forks() + ChronoUnit.DAYS.between(OffsetDateTime.now(), calculable.updatedAt());
  }
}
