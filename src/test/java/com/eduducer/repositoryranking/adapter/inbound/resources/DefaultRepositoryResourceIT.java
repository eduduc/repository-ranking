package com.eduducer.repositoryranking.adapter.inbound.resources;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eduducer.repositoryranking.IntegrationTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
@ActiveProfiles("integration-tests")
@DisplayName("Repository Ranking Service")
class DefaultRepositoryResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @AfterEach
  void reset() {
    WireMock.reset();
  }

  @DisplayName("Happy Path Scenarios")
  @Nested
  class HappyPathScenarios {

    @Test
    @DisplayName("Calls the GitHub API and calculates popularity, then return items")
    void repositoriesPopularity() throws Exception {

      stubFor(get(urlPathEqualTo("/search/repositories"))
          .withQueryParam("q", matching("^language:[A-Za-z]+(?: created:>\\d{4}-\\d{2}-\\d{2})?$"))
          .withHeader("X-GitHub-Api-Version", equalTo("2022-11-28"))
          .withHeader("User-Agent", equalTo("repository-ranking"))
          .willReturn(aResponse()
              .withStatus(200)
              .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github+json")
              .withBodyFile("github-api/search-repositories-response-200.json")));

      mockMvc.perform(
              MockMvcRequestBuilders.get("/api/v1/repositories/popularity")
                  .param("programming-language", "java")
                  .param("created-after", "2017-06-13")
          )
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.itemsTotalCount").value(6356490))
          .andExpect(jsonPath("$.items").isArray())
          .andExpect(jsonPath("$.items[0].fullName").value("Stirling-Tools/Stirling-PDF"))
          .andExpect(jsonPath("$.items[0].language").value("Java"))
          .andExpect(jsonPath("$.items[0].calculatedPopularity.value").value("325077.0"))
          .andExpect(jsonPath("$.items[0].calculatedPopularity.popularityScale").value("TOTAL_SCORE"));
    }

    @Test
    @DisplayName("If there are no items, returns empty response")
    void repositoriesPopularity_emptyItems() throws Exception {

      stubFor(get(urlPathEqualTo("/search/repositories"))
          .withQueryParam("q", matching("^language:[A-Za-z]+(?: created:>\\d{4}-\\d{2}-\\d{2})?$"))
          .withHeader("X-GitHub-Api-Version", equalTo("2022-11-28"))
          .withHeader("User-Agent", equalTo("repository-ranking"))
          .willReturn(aResponse()
              .withStatus(200)
              .withHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github+json")
              .withBodyFile("github-api/search-repositories-response-empty-items-200.json")));

      mockMvc.perform(
              MockMvcRequestBuilders.get("/api/v1/repositories/popularity")
                  .param("programming-language", "java")
                  .param("created-after", "2017-06-13")
          )
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.itemsTotalCount").value(6356490))
          .andExpect(jsonPath("$.items").isEmpty());
    }
  }

  @DisplayName("Error Handling Scenarios")
  @Nested
  class ErrorHandlingScenarios {
    @Test
    @DisplayName("If programming language parameter not set, returns Bad Request response")
    void repositoriesPopularity_programmingLanguage_mandatory() throws Exception {

      mockMvc.perform(
              MockMvcRequestBuilders.get("/api/v1/repositories/popularity")
                  .param("created-after", "2017-06-13")
          )
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
          .andExpect(jsonPath("$.status").value("400"))
          .andExpect(jsonPath("$.title").value("Bad Request"))
          .andExpect(jsonPath("$.detail").value("Required parameter 'programming-language' is not present."));
    }

    @Test
    @DisplayName("If createdAfter parameter is malformed, returns Bad Request response")
    void repositoriesPopularity_createdAfter_malformed() throws Exception {

      mockMvc.perform(
              MockMvcRequestBuilders.get("/api/v1/repositories/popularity")
                  .param("programming-language", "java")
                  .param("created-after", "2-AA-EEEE")
          )
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
          .andExpect(jsonPath("$.status").value("400"))
          .andExpect(jsonPath("$.title").value("Bad Request"))
          .andExpect(jsonPath("$.detail").value("Failed to convert 'created-after' with value: '2-AA-EEEE'"));
    }

    @Test
    @DisplayName("If external API returns 404, it handles the exception")
    void repositoriesPopularity_gitHub404() throws Exception {

      stubFor(get(urlPathEqualTo("/search/repositories"))
          .withQueryParam("q", matching("^language:[A-Za-z]+(?: created:>\\d{4}-\\d{2}-\\d{2})?$"))
          .withHeader("X-GitHub-Api-Version", equalTo("2022-11-28"))
          .withHeader("User-Agent", equalTo("repository-ranking"))
          .willReturn(aResponse()
              .withStatus(404)
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("github-api/search-repositories-response-404.json")));

      mockMvc.perform(
              MockMvcRequestBuilders.get("/api/v1/repositories/popularity")
                  .param("programming-language", "java")
                  .param("created-after", "2017-06-13")
          )
          .andExpect(status().isNotFound())
          .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
          .andExpect(jsonPath("$.status").value("404"))
          .andExpect(jsonPath("$.title").value("Not Found"))
          .andExpect(jsonPath("$.detail").value("Received Not Found response from external API"));
    }

    @Test
    @DisplayName("If external API returns 400, it handles the exception")
    void repositoriesPopularity_gitHub400() throws Exception {

      stubFor(get(urlPathEqualTo("/search/repositories"))
          .withQueryParam("q", matching("^language:[A-Za-z]+(?: created:>\\d{4}-\\d{2}-\\d{2})?$"))
          .withHeader("X-GitHub-Api-Version", equalTo("2022-11-28"))
          .withHeader("User-Agent", equalTo("repository-ranking"))
          .willReturn(aResponse()
              .withStatus(400)
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("github-api/search-repositories-response-400.json")));

      mockMvc.perform(
              MockMvcRequestBuilders.get("/api/v1/repositories/popularity")
                  .param("programming-language", "java")
                  .param("created-after", "2017-06-13")
          )
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE))
          .andExpect(jsonPath("$.status").value("400"))
          .andExpect(jsonPath("$.title").value("Bad Request"))
          .andExpect(jsonPath("$.detail").value("Received Bad Request response from external API"));
    }
  }
}
