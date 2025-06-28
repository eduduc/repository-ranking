package com.eduducer.repositoryranking.domain;

public record Popularity(
    String value,
    PopularityScale popularityScale
) {

  public enum PopularityScale {
    PERCENTAGE,
    ONE_TO_FIVE,
    TOTAL_SCORE
  }
}
