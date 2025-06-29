package com.eduducer.repositoryranking.adapter.outbound.github;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositorySearchResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OpenFeign client for calling the GitHub repositories search API
 * @see Cacheable to know more about the caching of API call results
 * @see Retry to know about resilience4j retry mechanism
 */
@FeignClient(name = "github-search-api")
public interface GitHubSearchApiClient {

  String QUERY_PARAMETER = "q";

  @SuppressWarnings("java:S7180") //Suppressed due to @Cacheable use on Feign client interfaces is recommended by Spring
  @Cacheable(cacheNames = "github-repositories-cache", key = "#searchQuery")
  @Retry(name = "gitHubRepositorySearchApi")
  @GetMapping(value = "/search/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
  GitHubRepositorySearchResponse searchRepositories(
      @RequestParam(QUERY_PARAMETER) final String searchQuery
  );
}
