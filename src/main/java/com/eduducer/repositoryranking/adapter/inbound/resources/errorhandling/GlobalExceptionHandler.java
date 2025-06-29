package com.eduducer.repositoryranking.adapter.inbound.resources.errorhandling;

import feign.FeignException.BadRequest;
import feign.FeignException.NotFound;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleInvalidInputException(final Exception e, final WebRequest request) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    problemDetail.setInstance(URI.create("/repositories/popularity"));
    return problemDetail;
  }

  @ExceptionHandler(NotFound.class)
  public ProblemDetail handleFeignExceptionNotFound(final NotFound e, final WebRequest request) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Received Not Found response from external API");
    problemDetail.setInstance(URI.create("/repositories/popularity"));
    return problemDetail;
  }

  @ExceptionHandler(BadRequest.class)
  public ProblemDetail handleFeignExceptionBadRequest(final BadRequest e, final WebRequest request) {
    final var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Received Bad Request response from external API");
    problemDetail.setInstance(URI.create("/repositories/popularity"));
    return problemDetail;
  }
}
