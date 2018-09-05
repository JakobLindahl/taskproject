package com.seha.TaskProject.service;

import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.exceptions.BadUserException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    public ArrayList<User> users = new ArrayList<>();

    @Before
    public void setup(){
        users.add(userService.createUser(new User("testingFirstName", "testingLastName", "testingUserName", 1L)));
    }

    @Test
    public void createUserWithValidData() {
        User fetchedUser = userService.getUserByUserNumber(users.get(0).getUserNumber()).get();
        assertEquals(fetchedUser.getFirstName(), users.get(0).getFirstName());
        assertEquals(fetchedUser.getLastName(), users.get(0).getLastName());
        assertEquals(fetchedUser.getUserName(), users.get(0).getUserName());
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


    @Test
    public void changeUsername(){
        userService.changeUserName("changingName", users.get(0).getUserNumber());
        User fetchUser = userService.getUserByUserNumber(users.get(0).getUserNumber()).get();
        assertTrue(fetchUser.getUserName().equalsIgnoreCase("changingName"));
        userService.inactivateUser(users.get(0).getUserNumber());
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
        boolean userNotFound = userService.inactivateUser(9018L);
        assertFalse(userNotFound);
    }


    @After
    public void breakDown(){
        users.forEach(u -> userService.inactivateUser(u.getUserNumber()));
    }

}
