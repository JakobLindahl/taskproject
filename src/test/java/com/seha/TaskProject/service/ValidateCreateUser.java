package com.seha.TaskProject.service;

import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.exceptions.BadUserException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ValidateCreateUser {

    @Autowired
    private UserService userService;

    @Test
    public void createUserWithValidData() {
        User user = userService.createUser(new User("FredrikFredrik", "FredrikFredrik", "FredrikFredrik", 1L));
        assertEquals("FredrikFredrik", user.getFirstName());
        assertEquals("FredrikFredrik", user.getLastName());
        assertEquals("FredrikFredrik", user.getUserName());
    }

    @Test
    public void createUserWithInvalidData() {
        boolean test = false;
        try {
            userService.createUser(new User("f", "f", "f", 2L));
        } catch (BadUserException e) {
            test = true;
        }
        assertTrue(test);
    }
}
