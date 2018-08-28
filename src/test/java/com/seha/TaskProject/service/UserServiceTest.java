package com.seha.TaskProject.service;

import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.exceptions.BadUserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    /*
    Successfully creates a User in the database.
     */
    @Test
    public void createUser() {
        userService.createUser(new User("Semi", "Turdean", "SemiTurdean", 1L));
    }

    /*
    Trying to create a User with to short userName(>10 letters) will throw a BadUserException error.
     */
    @Test(expected = BadUserException.class)
    public void createUserTestUsernName() throws Exception {
        userService.createUser(new User("Senad", "Hasic", "Hasic", 1L));
    }

    /*
    Trying to create a User with null inputs will throw a BadUserException error.
     */
    @Test(expected = BadUserException.class)
    public void createUserTestNullInput() throws Exception {
        userService.createUser(new User("","","", 1L));
    }

    /*
    Trying to inactivate a User with userId that exists will return a boolean value of true.
     */
    @Test
    public void inactivateUser() {
        boolean userFound = userService.inactivateUser(1001L);
        assertTrue(userFound);
    }

    /*
    Trying to inactivate a User with userId that does not exist will return a boolean value of false.
     */
    @Test
    public void inactivateUserNotFound() {
        boolean userNotFound = userService.inactivateUser(1018L);
        assertFalse(userNotFound);
    }
}
