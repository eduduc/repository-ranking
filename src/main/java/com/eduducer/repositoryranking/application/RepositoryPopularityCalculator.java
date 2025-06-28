package com.eduducer.repositoryranking.application;

public interface RepositoryPopularityCalculator<I,O> {

  O calculatePopularity(I calculable);

}
