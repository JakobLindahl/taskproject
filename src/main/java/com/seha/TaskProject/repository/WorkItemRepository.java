package com.seha.TaskProject.repository;

import com.seha.TaskProject.data.User;
import com.seha.TaskProject.data.WorkItem;
import com.seha.TaskProject.data.workitemenum.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long> {

    List<WorkItem> findAllByUser(User user);

    List<WorkItem> findAll();

    List<WorkItem> findWorkItemsByUserId(Long userId);

    List<WorkItem> findWorkItemsByStatus(Status status);

    Page<WorkItem> findAll(Pageable pageable);

}