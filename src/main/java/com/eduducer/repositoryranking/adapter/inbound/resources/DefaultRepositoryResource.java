package com.eduducer.repositoryranking.adapter.inbound.resources;

import com.eduducer.repositoryranking.application.GitHubSearchApiService;
import com.eduducer.repositoryranking.domain.RepositoriesPopularityResponse;
import java.time.LocalDate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    produces = MediaType.APPLICATION_JSON_VALUE,
    value = RepositoryResource.BASE_PATH
)
public class DefaultRepositoryResource implements RepositoryResource {

  private final GitHubSearchApiService gitHubSearchApiService;

  public DefaultRepositoryResource(final GitHubSearchApiService gitHubSearchApiService) {
    this.gitHubSearchApiService = gitHubSearchApiService;
  }

  @GetMapping("/popularity")
  @Override
  public RepositoriesPopularityResponse repositoriesPopularity(
      @RequestParam(value = "programming-language") final String programmingLanguage,
      @RequestParam(value = "created-after", required = false) final LocalDate createdAfter,
      @RequestParam(value = "page-size", defaultValue = "30") final int pageSize,
      @RequestParam(value = "page", defaultValue = "1") final int page
  ) {
    return gitHubSearchApiService.searchAndRatePopularity(programmingLanguage, createdAfter, pageSize, page);
  }
}
