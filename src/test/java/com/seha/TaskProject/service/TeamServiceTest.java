package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Team;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.exceptions.BadTeamException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    @Test
    public void setUserToTeam() throws Exception {
        teamService.setUserToTeam(2L, 1005L);
    }
    */

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