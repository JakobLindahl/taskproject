package com.seha.TaskProject.service;

import com.seha.TaskProject.service.exceptions.BadTeamException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    TeamService teamService;

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
}