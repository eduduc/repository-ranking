package com.eduducer.repositoryranking.adapter.outbound.github;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositorySearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "github-search-api"
)
public interface GitHubSearchApiClient {

  @GetMapping(value = "/search/repositories", produces = MediaType.APPLICATION_JSON_VALUE)
  GitHubRepositorySearchResponse searchRepositories(
      @RequestParam("q") final String searchQuery
  );

}
