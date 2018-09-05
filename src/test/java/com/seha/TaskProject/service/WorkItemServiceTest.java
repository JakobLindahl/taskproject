package com.seha.TaskProject.service;

import com.seha.TaskProject.service.exceptions.BadUserException;
import com.seha.TaskProject.service.exceptions.BadWorkitemException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkItemServiceTest {

    /*
    Adding a Workitem to a User with workItemId and userId.
    Will throw BadWorkItemException if the WorkItem already has been added to user.
    Works only with the unique id that is generated from database and not the auto generated id in UserService.
    */

    @Test
    public void addWorkItemByUserId() {
        workItemService.addWorkItemByUserId(2L, 8L);
    }

    @Autowired
    WorkItemService workItemService;

    /*
    Trying to add a WorkItem to a userId witho non existent workItemId will throw a BadWorkItemrException error.
     */
    @Test(expected = BadWorkitemException.class)
    public void addWorkItemByUserIdWithWrongWorkItemId() throws Exception {
        workItemService.addWorkItemByUserId(18282L, 1L);
    }

    /*
    Trying to add a WorkItem to a userId witho non existent workItemId will throw a BadWorkItemrException error.
    */
    @Test(expected = BadUserException.class)
    public void addWorkItemByUserIdWithWrongUserId() throws Exception {
        workItemService.addWorkItemByUserId(2L, 70000L);
    }
}