package com.eduducer.repositoryranking.adapter.inbound.resources;

import com.eduducer.repositoryranking.domain.RepositoriesPopularityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import org.springframework.http.MediaType;

public interface RepositoryResource {

  @SuppressWarnings("java:S1075")
  String BASE_PATH = "/api/v1/repositories";

  @Operation(
      summary = "Search repositories and calculate popularity",
      description = "Returns a list of repositories, each one with a calculated popularity score",
      operationId = "repositoriesPopularity",
      tags = "Repositories"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid parameters",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(
              mediaType = "application/problem+json"
          )
      )}
  )
  RepositoriesPopularityResponse repositoriesPopularity(
      @NotBlank
      @Parameter(description = "Search repositories by this programming language", required = true, example = "java")
      final String programmingLanguage,
      @Parameter(
          description = "Search repositories created after this date",
          required = true,
          example = "2023-01-01",
          schema = @Schema(implementation = LocalDate.class))
      final LocalDate createdAfter
  );
}
