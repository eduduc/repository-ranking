package com.eduducer.repositoryranking.application;

import com.eduducer.repositoryranking.domain.RepositoriesPopularityResponse;
import java.time.LocalDate;

public interface GitHubSearchApiService {

  RepositoriesPopularityResponse searchAndRatePopularity(
      final String language,
      final LocalDate createdAfter,
      final int pageSize,
      final int page
  );

}
