package com.eduducer.repositoryranking.adapter.outbound.github.mapper;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositoryItem;
import com.eduducer.repositoryranking.domain.RepositoryItem;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(
    componentModel = ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {GitHubSimpleUserMapper.class}
)
public interface GitHubRepositoryItemMapper {

  @Mapping(target = "stars", source = "stargazersCount")
  RepositoryItem map(GitHubRepositoryItem gitHubRepositoryItem);
}
