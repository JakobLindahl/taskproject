package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Issue;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.data.WorkItem;
import com.seha.TaskProject.data.workitemenum.Status;
import com.seha.TaskProject.repository.IssueRepository;
import com.seha.TaskProject.repository.UserRepository;
import com.seha.TaskProject.repository.WorkItemRepository;
import com.seha.TaskProject.service.exceptions.BadIssueException;
import com.seha.TaskProject.service.exceptions.BadTeamException;
import com.seha.TaskProject.service.exceptions.BadUserException;
import com.seha.TaskProject.service.exceptions.BadWorkitemException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class WorkItemService {

    private final WorkItemRepository workItemRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public WorkItemService(WorkItemRepository workItemRepository, IssueRepository issueRepository, UserRepository userRepository) {
        this.workItemRepository = workItemRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public Page<WorkItem> getAllWorkItemsByPageAndSize(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        return workItemRepository.findAll(pageable);
    }

    public List<WorkItem> getWorkItemsByDate(String startDate, String endDate, String status) {
        validateStatus(status);
        LocalDate parsedStartDate = validateDate(startDate);
        LocalDate parsedEndDate = validateDate(endDate);
        List<WorkItem> workItems = workItemRepository.findAll().stream().filter(w -> w.getUpdateDate().compareTo(parsedStartDate) >= 0 &&
                w.getUpdateDate().compareTo(parsedEndDate) <= 0 &&
                w.getStatus().toString().equals(status))
                .collect(Collectors.toList());
        if (workItems.isEmpty()) {
            throw new BadWorkitemException("No workitems for that time period.");
        }
        return workItems;
    }

    public void addIssueToWorkItem(Long id, Issue issue) {
        validateWorkItem(id);
        workItemRepository.findById(id).ifPresent(w -> {
            w.setStatus(Status.UNSTARTED);
            issue.setWorkItem(w);
            w.setIssue(issue);
            issueRepository.save(issue);
            workItemRepository.save(w);
        });
    }

    public WorkItem createWorkItem(WorkItem workItem) {
        if (workItem.getName() == null || workItem.getDescription() == null) {
            throw new BadWorkitemException("All required values for the workItem has not been assigned");
        }
        return workItemRepository.save(new WorkItem(workItem.getName(), workItem.getDescription()));
    }

    public boolean setStatusOnWorkItem(Long id, String status) {
        Optional<WorkItem> workItems = workItemRepository.findById(id);
        if (workItems.isPresent()) {
            validateStatus(status);
            workItems.get().setStatus(Status.valueOf(status));
            workItemRepository.save(workItems.get());
            return true;
        }
        return false;
    }

    public Optional<WorkItem> getWorkItem(Long id) {
        return workItemRepository.findById(id);
    }

    public List<WorkItem> getAllWorkItems() {
        return workItemRepository.findAll();
    }

    public boolean deleteWorkItem(Long id) {
        if (workItemRepository.existsById(id)) {
            workItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void addWorkItemByUserId(Long workItemId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<WorkItem> workItem = workItemRepository.findById(workItemId);
        if (!user.isPresent()) {
            throw new BadUserException("No User with that id");
        } else if (!workItem.isPresent()) {
            throw new BadWorkitemException("No Workitem with that id");
        } else if (user.get().getWorkItems().size() > 4) {
            throw new BadWorkitemException("To many Workitems for that user");
        } else if (!user.get().getActive()) {
            throw new BadUserException("User is not active");
        }
        user.get().setWorkItems(workItem.get());
        workItemRepository.save(workItem.get());
    }

    public void addWorkItemByHelperId(Long workItemId, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<WorkItem> workItem = workItemRepository.findById(workItemId);
        if (!user.isPresent()) {
            throw new BadUserException("No User with that id");
        } else if (!workItem.isPresent()) {
            throw new BadWorkitemException("No Workitem with that id");
        } else if (!user.get().getActive()) {
            throw new BadUserException("User is not active");
        } else if ((workItem.get().getUser()) == null) {
            throw new BadWorkitemException("No user assigned to workItem");
        }
        workItem.get().addHelper(user.get());
        workItemRepository.save(workItem.get());
    }

    public List<WorkItem> getAllWorkItemsByTeamId(Long teamId) {
        List<User> users = userRepository.findUsersByTeamId(teamId);
        if (users.isEmpty()) {
            throw new BadTeamException("No users for team with id: " + teamId);
        }
        return workItemRepository.findAll().stream()
                .filter(w -> users.stream().anyMatch(u -> u.getWorkItems().stream().anyMatch(wu -> wu.getId().equals(w.getId()))))
                .collect(Collectors.toList());
    }

    public List<WorkItem> getAllWorkItemsByUserId(Long userId) {
        List<WorkItem> workItems = workItemRepository.findWorkItemsByUserId(userId);
        if (workItems.isEmpty()) {
            throw new BadWorkitemException("No workitems for user with id: " + userId);
        }
        return workItems;
    }

    public List<WorkItem> getAllWorkItemsByDescription(String description) {
        List<WorkItem> workItems = workItemRepository.findAll().stream()
                .filter(w -> w.getDescription().contains(description))
                .collect(Collectors.toList());
        if (workItems.isEmpty()) {
            throw new BadWorkitemException("No workitems with description: " + description);
        }
        return workItems;
    }

    public List<WorkItem> getAllWorkItemsWithIssues() {
        List<WorkItem> workItems = workItemRepository.findAll().stream()
                .filter(w -> issueRepository.findAll().stream()
                        .anyMatch(i -> i.getWorkItem().getId().equals(w.getId()))).collect(Collectors.toList());
        if (workItems.isEmpty()) {
            throw new BadWorkitemException("No workitems found with issues");
        }
        return workItems;
    }

    public List<WorkItem> getAllWorkItemsByStatus(Status status) {
        List<WorkItem> workItems = workItemRepository.findWorkItemsByStatus(status);
        if (workItems.isEmpty()) {
            throw new BadWorkitemException("No workitems with status: " + status);
        }
        return workItems;
    }

    private void validateWorkItem(Long id) {
        if (!workItemRepository.findById(id).isPresent()) {
            throw new BadWorkitemException("No workitem was found with id: " + id);
        }
        if (!workItemRepository.findById(id).get().getStatus().toString().equals("DONE")) {
            throw new BadIssueException("Cant add an issue to a workitem that is not DONE");
        }
    }

    private void validateStatus(String status) {
        if (!status.equals("STARTED") && !status.equals("UNSTARTED") && !status.equals("DONE")) {
            throw new BadWorkitemException("status=? , do not contain DONE, STARTED or UNSTARTED");
        }
    }

    private LocalDate validateDate(String date) {
        if (date == null) {
            throw new BadWorkitemException("Enter information for both dates.");
        }
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new BadWorkitemException("Invalid date format, please enter date by YYYY-mm-dd.");
        }
    }
}