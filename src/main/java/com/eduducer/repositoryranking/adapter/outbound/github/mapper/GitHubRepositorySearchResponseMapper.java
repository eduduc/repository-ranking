package com.eduducer.repositoryranking.adapter.outbound.github.mapper;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubRepositorySearchResponse;
import com.eduducer.repositoryranking.domain.RepositoriesPopularityResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(
    componentModel = ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {GitHubRepositoryItemMapper.class}
)
public interface GitHubRepositorySearchResponseMapper {

  @Mapping(target = "itemsTotalCount", source = "totalCount")
  RepositoriesPopularityResponse map(GitHubRepositorySearchResponse gitHubRepositorySearchResponse);

}
