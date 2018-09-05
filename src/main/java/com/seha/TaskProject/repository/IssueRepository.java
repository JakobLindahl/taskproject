package com.seha.TaskProject.repository;

import com.seha.TaskProject.data.Issue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IssueRepository extends CrudRepository<Issue, Long>, PagingRepository<Issue> {

    List<Issue> findAll();

    Issue findByWorkItemId(Long workItemId);
}