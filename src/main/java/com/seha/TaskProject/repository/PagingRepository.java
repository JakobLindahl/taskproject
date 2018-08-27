package com.seha.TaskProject.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
interface PagingRepository<T> extends PagingAndSortingRepository<T, Long> {

}


