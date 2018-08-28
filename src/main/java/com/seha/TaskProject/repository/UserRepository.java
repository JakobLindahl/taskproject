package com.seha.TaskProject.repository;

import com.seha.TaskProject.data.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, PagingRepository<User> {

    @Query("SELECT MAX(userNumber) from User")
    Optional<Long> getHighestUserNumber();

    Optional<User> findUserByuserNumber(Long id);

    List<User> findAll();

    List<User> getAllByTeamsId(Long id);

    List<User> findUsersByTeamsId(Long teamId);
}