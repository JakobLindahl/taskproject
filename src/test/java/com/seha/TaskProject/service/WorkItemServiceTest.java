package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Issue;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.data.WorkItem;
import com.seha.TaskProject.data.workitemenum.Status;
import com.seha.TaskProject.service.exceptions.BadUserException;
import com.seha.TaskProject.service.exceptions.BadWorkitemException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkItemServiceTest {

    @Autowired
    WorkItemService workItemService;
    @Autowired
    UserService userService;
    @Autowired
    IssueService issueService;
    public ArrayList<WorkItem> workItems = new ArrayList<>();
    public ArrayList<User> users = new ArrayList<>();

    @Before
    public void setup(){
        workItems.add(workItemService.createWorkItem(new WorkItem("testItem", "Make sure you test this!")));
        users.add(userService.createUser(new User("TestUserFName", "TestUserLName", "TestUserName", -1L)));
    }

    /*
    Adding a Workitem to a User with workItemId and userId.
    Will throw BadWorkItemException if the WorkItem already has been added to user.
    Works only with the unique id that is generated from database and not the auto generated id in UserService.
    */

    @Test
    public void addWorkItemByUserId() {
        workItemService.addWorkItemByUserId(workItems.get(0).getId(), users.get(0).getId());
    }
    /*
    Trying to add a WorkItem to a userId witho non existent workItemId will throw a BadWorkItemrException error.
     */
    @Test(expected = BadWorkitemException.class)
    public void addWorkItemByUserIdWithWrongWorkItemId() throws Exception {
        workItemService.addWorkItemByUserId(98282L, users.get(0).getId());
    }

    /*
    Trying to add a WorkItem to a userId witho non existent workItemId will throw a BadWorkItemrException error.
    */
    @Test(expected = BadUserException.class)
    public void addWorkItemByUserIdWithWrongUserId() throws Exception {
        workItemService.addWorkItemByUserId(2L, 90000L);
    }

    @Test
    public void assignIssueToWorkItem(){
        Issue issue = new Issue("testing");
        workItemService.setStatusOnWorkItem(workItems.get(0).getId(), "DONE");
        workItemService.addIssueToWorkItem(workItems.get(0).getId(), issue);
        Issue fetchIssue = issueService.getIssueByWorkItemId(workItems.get(0).getId());
        assertTrue(issue.getDescription().equalsIgnoreCase(fetchIssue.getDescription()));
    }

    @After
    public void breakDown(){
        workItems.forEach(w -> workItemService.deleteWorkItem(w.getId()));
        users.forEach(u -> userService.inactivateUser(u.getUserNumber()));
    }

}