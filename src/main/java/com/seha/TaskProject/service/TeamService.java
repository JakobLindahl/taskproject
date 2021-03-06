package com.seha.TaskProject.service;

import com.seha.TaskProject.data.Team;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.repository.TeamRepository;
import com.seha.TaskProject.repository.UserRepository;
import com.seha.TaskProject.service.exceptions.BadTeamException;
import com.seha.TaskProject.service.exceptions.ToManyTeamsException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public Team createTeam(Team team) {
        if (team.getName() == null || team.getActive() == null) {
            throw new BadTeamException("All required values for the team has not been assigned");
        }
        return teamRepository.save(team);
    }

    public boolean updateTeam(Long id, Team team) {
        Optional<Team> teams = teamRepository.findById(id);
        if (teams.isPresent()) {
            teams.get().setName(team.getName());
            teamRepository.save(teams.get());
            return true;
        }
        return false;
    }

    public boolean inactivateTeam(Long id) {
        Optional<Team> teams = teamRepository.findById(id);
        if (teams.isPresent()) {
            teams.get().setActive(false);
            teamRepository.save(teams.get());
            return true;
        }
        return false;
    }

    public Iterable<Team> getAllTeams() {
        Iterable<Team> teams = teamRepository.findAll(Sort.by("name"));
        if (!teams.iterator().hasNext()) {
            throw new BadTeamException("No teams in database.");
        }
        return teams;
    }

    public void setUserToTeam(Long id, Long userNumber) {
        Optional<Team> team = teamRepository.findById(id);
        Optional<User> user = userRepository.findUserByuserNumber(userNumber);
        if (!team.isPresent()) {
            throw new BadTeamException("No team matching that ID was found");
        } else if (!user.isPresent()) {
            throw new BadTeamException("No user matching that ID was found");
        } else if (teamRepository.findTeamsByUser(user.get().getId()).size() < 3) {
            validateTeam(team.get());
            user.ifPresent(u -> {
                    team.get().addUser(u);
                    teamRepository.save(team.get());
            });
        } else
            throw new ToManyTeamsException("User are already in 3 teams");
    }

    private void validateTeam(Team team) {
        if (team.getUsers().size() > 9) {
            throw new BadTeamException("Team cannot have more than 10 users");
        }
    }
}