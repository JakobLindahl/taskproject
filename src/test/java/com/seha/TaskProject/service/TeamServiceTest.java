package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Team;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.exceptions.BadTeamException;
import com.seha.TaskProject.service.exceptions.ToManyTeamsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    TeamService teamService;
    @Autowired
    UserService userService;

    /*
    Unable to run more then once for unique userId, because user will be added and Excpetion thwrown.
     */
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

    @Test
    public void setUserToTeam() throws Exception {
        teamService.setUserToTeam(2L, 1005L);
    }

    /*
    Will throw a BadTeamException since there is no Team with the id given in the parameters.
     */
    @Test(expected = BadTeamException.class)
    public void setUserToTeamWithNonExistentTeam() throws Exception {
        teamService.setUserToTeam(5L, 1006L);
    }

    /*
    Will throw a BadTeamException since there is no User with the id given in the parameters.
     */
    @Test(expected = BadTeamException.class)
    public void setUserToTeamWithNoUserId() throws Exception {
        teamService.setUserToTeam(2L, 1030L);
    }

    @Test
    public void remove() {
        //TU03 inactivation of valid team
        /*
            testing function by creating a new team and inactivating it and then
            catching an exception thats thrown if object is set as inactive.
         */
        boolean thrown = false;
        Team t = teamService.createTeam(new Team("testing",true));
        teamService.inactivateTeam(t.getId());

        teamService.getAllTeams().forEach(team -> {
            if(team.getId().equals(t.getId())){
                assertFalse(team.getActive());
            }
        } );


    }


}