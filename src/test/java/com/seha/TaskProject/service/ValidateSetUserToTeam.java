package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Team;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.exceptions.ToManyTeamsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ValidateSetUserToTeam {

    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    private User user;
    private Team team;
    private Team team2;
    private Team team3;
    private Team team4;

    @Before
    public void setup() {
        user = userService.createUser(new User("FredrikFredrik", "FredrikFredrik", "FredrikFredrik", 1L));
        team = teamService.createTeam(new Team("BÖRJEFÖRENING", true));
        team2 = teamService.createTeam(new Team("BÖRJEFÖRENING v2", true));
        team3 = teamService.createTeam(new Team("BÖRJEFÖRENING v3", true));
        team4 = teamService.createTeam(new Team("BÖRJEFÖRENING v4", true));
        teamService.setUserToTeam(team.getId(), user.getUserNumber());
        teamService.setUserToTeam(team2.getId(), user.getUserNumber());
        teamService.setUserToTeam(team3.getId(), user.getUserNumber());
    }

    /*
        Test is about to trying to assign a fourth team to a user, it should be an exception and then the test will pass.
     */

    @Test
    public void SetUserToTeam() {
        boolean test = false;
        try {
            teamService.setUserToTeam(team4.getId(), user.getUserNumber());
        } catch (ToManyTeamsException e) {
            test = true;
        }
        assertTrue(test);
    }

    @After
    public void release() {
        teamService.inactivateTeam(team.getId());
        teamService.inactivateTeam(team2.getId());
        teamService.inactivateTeam(team3.getId());
        teamService.inactivateTeam(team4.getId());
        userService.inactivateUser(user.getUserNumber());
    }


}
