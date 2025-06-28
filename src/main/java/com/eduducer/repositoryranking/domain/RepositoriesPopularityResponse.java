package com.eduducer.repositoryranking.domain;

import java.util.List;

public record RepositoriesPopularityResponse(int itemsTotalCount, List<RepositoryItem> items) {

}
