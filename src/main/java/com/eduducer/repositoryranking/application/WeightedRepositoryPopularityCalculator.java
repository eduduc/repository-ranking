package com.eduducer.repositoryranking.application;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositoryItem;
import com.eduducer.repositoryranking.domain.Popularity;
import com.eduducer.repositoryranking.domain.Popularity.PopularityScale;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WeightedRepositoryPopularityCalculator implements RepositoryPopularityCalculator<GitHubRepositoryItem, Popularity> {

  private static final Logger LOG = LoggerFactory.getLogger(WeightedRepositoryPopularityCalculator.class);

  private final PopularityCalculatorWeightsConfiguration popularityCalculatorWeightsConfiguration;

  public WeightedRepositoryPopularityCalculator(final PopularityCalculatorWeightsConfiguration popularityCalculatorWeightsConfiguration) {
    this.popularityCalculatorWeightsConfiguration = popularityCalculatorWeightsConfiguration;
  }

  @Override
  public Popularity calculatePopularity(final GitHubRepositoryItem calculable) {
    final var popularity = getPopularity(calculable);
    return new Popularity(String.valueOf(popularity), PopularityScale.TOTAL_SCORE);
  }

  private double getPopularity(final GitHubRepositoryItem calculable) {
    LOG.atDebug().log("Calculating popularity for {}, using configuration {}", calculable, popularityCalculatorWeightsConfiguration);
    final long daysSinceUpdate = ChronoUnit.DAYS.between(calculable.updatedAt(), OffsetDateTime.now());
    final double recencyScore = 100.0 / (daysSinceUpdate + 1);
    final double rawScore =
        calculable.stargazersCount() * popularityCalculatorWeightsConfiguration.stars()
            + calculable.forks() * popularityCalculatorWeightsConfiguration.forks()
            + recencyScore * popularityCalculatorWeightsConfiguration.updatesRecency();

    return Math.round(rawScore);
  }
}
