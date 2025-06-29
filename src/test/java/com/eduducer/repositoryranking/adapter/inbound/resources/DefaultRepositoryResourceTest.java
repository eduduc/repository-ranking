package com.eduducer.repositoryranking.adapter.inbound.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eduducer.repositoryranking.application.GitHubSearchApiService;
import com.eduducer.repositoryranking.domain.Popularity;
import com.eduducer.repositoryranking.domain.Popularity.PopularityScale;
import com.eduducer.repositoryranking.domain.RepositoriesPopularityResponse;
import com.eduducer.repositoryranking.domain.RepositoryHostingUser;
import com.eduducer.repositoryranking.domain.RepositoryItem;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("Default Repository API")
@WebMvcTest(DefaultRepositoryResource.class)
@ExtendWith({MockitoExtension.class})
class DefaultRepositoryResourceTest {

  @MockitoBean
  private GitHubSearchApiService gitHubSearchApiService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Searches and retrieves repositories with calculated repository popularity")
  void repositoriesPopularity() throws Exception {
    final var response = new RepositoriesPopularityResponse(23, getRepositoryItems());

    when(gitHubSearchApiService.searchAndRatePopularity(anyString(), any(), anyInt(), anyInt())).thenReturn(response);

    mockMvc.perform(
            get("/api/v1/repositories/popularity")
                .param("programming-language", "kotlin")
                .param("created-after", "2017-06-13")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.itemsTotalCount").value(23))
        .andExpect(jsonPath("$.items").isArray());

    final var expectedLocalDate = LocalDate.of(2017, 6, 13);
    verify(gitHubSearchApiService, times(1))
        .searchAndRatePopularity("kotlin", expectedLocalDate, 30, 1);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @DisplayName("No programming language query parameter results in Bad Request response")
  void noProgrammingLanguageQueryParameterBadRequestResponse(final String programmingLanguage) throws Exception {
    mockMvc.perform(
            get("/api/v1/repositories/popularity")
                .param("programming-language", programmingLanguage)
                .param("created-after", "2017-06-13")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
        .andExpect(jsonPath("$.title").value("Bad Request"))
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    verify(gitHubSearchApiService, times(0)).searchAndRatePopularity(anyString(), any(), anyInt(), anyInt());
  }

  @Test
  @DisplayName("Badly formatted 'created-after' parameter results in Bad Request response")
  void wrongCreatedAfterFormatBadRequestResponse() throws Exception {
    mockMvc.perform(
            get("/api/v1/repositories/popularity")
                .param("programming-language", "ruby")
                .param("created-after", "2ASD-XX-AA")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
        .andExpect(jsonPath("$.title").value("Bad Request"))
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    verify(gitHubSearchApiService, times(0)).searchAndRatePopularity(anyString(), any(), anyInt(), anyInt());
  }

  @Test
  @DisplayName("Badly formatted 'page-size' parameter results in Bad Request response")
  void wrongPageSizeFormatBadRequestResponse() throws Exception {
    mockMvc.perform(
            get("/api/v1/repositories/popularity")
                .param("programming-language", "ruby")
                .param("page-size", "800")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
        .andExpect(jsonPath("$.title").value("Bad Request"))
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    verify(gitHubSearchApiService, times(0)).searchAndRatePopularity(anyString(), any(), anyInt(), anyInt());
  }

  @Test
  @DisplayName("Badly formatted 'page' parameter results in Bad Request response")
  void wrongPageFormatBadRequestResponse() throws Exception {
    mockMvc.perform(
            get("/api/v1/repositories/popularity")
                .param("programming-language", "ruby")
                .param("page", "ABCD")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
        .andExpect(jsonPath("$.title").value("Bad Request"))
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));

    verify(gitHubSearchApiService, times(0)).searchAndRatePopularity(anyString(), any(), anyInt(), anyInt());
  }

  @Test
  @DisplayName("Exceptions thrown from service layers result in Internal Server Error response")
  void serviceExceptionInternalServerErrorResponse() throws Exception {
    when(gitHubSearchApiService.searchAndRatePopularity(anyString(), any(), anyInt(), anyInt()))
        .thenThrow(new RuntimeException("Something went wrong"));

    mockMvc.perform(
            get("/api/v1/repositories/popularity")
                .param("programming-language", "ruby")
        )
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
        .andExpect(jsonPath("$.title").value("Internal Server Error"))
        .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()));

    verify(gitHubSearchApiService, times(1)).searchAndRatePopularity(anyString(), isNull(), anyInt(), anyInt());
  }


  private List<RepositoryItem> getRepositoryItems() {
    OffsetDateTime created1 = OffsetDateTime.parse("2023-01-10T08:30:00Z");
    OffsetDateTime updated1 = OffsetDateTime.parse("2023-02-15T10:00:00Z");
    OffsetDateTime pushed1 = OffsetDateTime.parse("2023-02-15T10:05:00Z");

    OffsetDateTime created2 = OffsetDateTime.parse("2022-11-01T12:00:00Z");
    OffsetDateTime updated2 = OffsetDateTime.parse("2023-01-05T09:15:00Z");
    OffsetDateTime pushed2 = OffsetDateTime.parse("2023-01-05T09:20:00Z");

    RepositoryHostingUser owner1 = new RepositoryHostingUser(
        "u123",
        "https://github.com/alice",
        "alice",
        "https://avatars.github.com/alice.png",
        "User",
        "GitHub"
    );

    RepositoryHostingUser owner2 = new RepositoryHostingUser(
        "u456",
        "https://github.com/bob",
        "bob",
        "https://avatars.github.com/bob.png",
        "User",
        "GitHub"
    );

    RepositoryItem repo1 = new RepositoryItem(
        "repo-123",
        "alice/sample-project",
        "A sample Java project",
        "Java",
        owner1,
        "https://github.com/alice/sample-project",
        created1,
        updated1,
        pushed1,
        128,
        256,
        128,
        32,
        8,
        new Popularity("123", PopularityScale.TOTAL_SCORE)
    );

    RepositoryItem repo2 = new RepositoryItem(
        "repo-456",
        "bob/utility-lib",
        "A handy utility library",
        "Kotlin",
        owner2,
        "https://github.com/bob/utility-lib",
        created2,
        updated2,
        pushed2,
        64,
        1024,
        512,
        128,
        16,
        new Popularity("336", PopularityScale.TOTAL_SCORE)
    );
    return List.of(repo1, repo2);
  }
}
