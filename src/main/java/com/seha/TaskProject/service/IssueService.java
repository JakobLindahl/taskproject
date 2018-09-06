package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Issue;
import com.seha.TaskProject.repository.IssueRepository;
import com.seha.TaskProject.service.exceptions.BadIssueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Page<Issue> getAllIssuesByPageAndSize(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        return issueRepository.findAll(pageable);
    }

    public boolean updateIssue(Long id, Issue issue) {
        Optional<Issue> result = issueRepository.findById(id);
        if (result.isPresent()) {
            result.get().setDescription(issue.getDescription());
            issueRepository.save(result.get());
            return true;
        }
        return false;
    }

    public Iterable<Issue> getAllIssues() {
        Iterable<Issue> issues = issueRepository.findAll(Sort.by("description"));
        if(!issues.iterator().hasNext()) {
            throw new BadIssueException("No issues in database.");
        }
        return issues;
    }

    public Issue getIssueByWorkItemId(Long workItemId){

        Issue issue = issueRepository.findByWorkItemId(workItemId);
        if(issue == null){
            throw new BadIssueException("couldnt find issue");
        } else
        return issue;
    }

}