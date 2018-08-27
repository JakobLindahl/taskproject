package com.seha.TaskProject.repository;

import com.seha.TaskProject.data.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long>, PagingRepository<Team> {

    List<Team> findAll();

    Optional<Team> findByName(String name);
}