package com.eduducer.repositoryranking.adapter.outbound.github.mapper;

import com.eduducer.repositoryranking.adapter.outbound.github.model.GitHubSimpleUser;
import com.eduducer.repositoryranking.domain.RepositoryHostingUser;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(
    componentModel = ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface GitHubSimpleUserMapper {

  @Mapping(target = "userType", source="type")
  @Mapping(target = "hostingPlatformName", constant="github")
  RepositoryHostingUser map(GitHubSimpleUser gitHubSimpleUser);

}
