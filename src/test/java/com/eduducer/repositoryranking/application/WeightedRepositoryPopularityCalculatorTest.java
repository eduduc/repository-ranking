package com.eduducer.repositoryranking.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositoryItem;
import com.eduducer.repositoryranking.domain.Popularity.PopularityScale;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeightedRepositoryPopularityCalculatorTest {

  @Mock
  private PopularityCalculatorWeightsConfiguration configuration;

  private WeightedRepositoryPopularityCalculator popularityCalculator;

  @BeforeEach
  void setUp() {
    popularityCalculator = new WeightedRepositoryPopularityCalculator(configuration);
  }

  @Mock
  private GitHubRepositoryItem repositoryItem;

  @Test
  void calculatePopularity() {
    final var lastUpdated = OffsetDateTime.now().minusDays(1);

    when(repositoryItem.stargazersCount()).thenReturn(10);
    when(repositoryItem.forks()).thenReturn(2);
    when(repositoryItem.updatedAt()).thenReturn(lastUpdated);

    when(configuration.stars()).thenReturn(5);
    when(configuration.forks()).thenReturn(2);
    when(configuration.updatesRecency()).thenReturn(8);

    final var popularity = popularityCalculator.calculatePopularity(repositoryItem);

    assertEquals("454.0", popularity.value());
    assertEquals(PopularityScale.TOTAL_SCORE, popularity.popularityScale());
  }

}
