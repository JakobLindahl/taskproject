package com.seha.TaskProject.repository;

import com.seha.TaskProject.data.Team;
import com.seha.TaskProject.data.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long>, PagingRepository<Team> {

    List<Team> findAll();

    Optional<Team> findByName(String name);

    @Query("select distinct t from Team t inner join t.users u where u.id = :id")
    List<Team> findTeamsByUser(@Param("id") Long id);
}