package com.eduducer.repositoryranking.adapter.outbound.github.mapper;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositoryItem;
import com.eduducer.repositoryranking.application.RepositoryPopularityCalculator;
import com.eduducer.repositoryranking.domain.Popularity;
import com.eduducer.repositoryranking.domain.RepositoryItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {GitHubSimpleUserMapper.class}
)
public abstract class GitHubRepositoryItemMapper {

  @Autowired
  protected RepositoryPopularityCalculator<GitHubRepositoryItem, Popularity> popularityCalculator;

  @Mapping(target = "stars", source = "stargazersCount")
  @Mapping(target = "calculatedPopularity", source = "gitHubRepositoryItem", qualifiedByName = "calculatePopularity")
  public abstract RepositoryItem map(GitHubRepositoryItem gitHubRepositoryItem);

  @Named("calculatePopularity")
  Popularity calculatePopularity(final GitHubRepositoryItem gitHubRepositoryItem) {
    return popularityCalculator.calculatePopularity(gitHubRepositoryItem);
  }
}
