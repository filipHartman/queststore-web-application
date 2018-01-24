package com.codecool.idontspeakjava.queststore.controllers.codecooler;

import com.codecool.idontspeakjava.queststore.database.TeamsDAO;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageTeamsController {

    public static final Logger log = LoggerFactory.getLogger(ManageTeamsController.class);
    public static final String NEW_TEAM = "1";
    public static final String JOIN_TEAM = "2";
    public static final String LEAVE_TEAM = "3";
    public static final String EXIT = "0";
    boolean userDecidedToExitController = false;
    private User codecooler;
    private TeamsDAO teamsDAO;
    private CodecoolerView view;
    private Optional<Team> optionalTeam;

    public ManageTeamsController(User codecooler, TeamsDAO teamsDAO, CodecoolerView view) {
        this.codecooler = codecooler;
        this.teamsDAO = teamsDAO;
        this.view = view;
        this.optionalTeam = Optional.ofNullable(teamsDAO.getUserTeam(codecooler));
    }

    public void start() {
        while (!userDecidedToExitController) {
            view.clearScreen();
            view.showManageTeamMenu(codecooler.getFirstName(), optionalTeam.isPresent() ? optionalTeam.get().getName() : "None");
            String optionChosenByUser = view.getUserInput();
            runNextSectionsBasedOnUserChosenOption(optionChosenByUser);
        }
    }

    private void runNextSectionsBasedOnUserChosenOption(String optionChosenByUser) {
        view.clearScreen();
        try {
            switch (optionChosenByUser) {
                case NEW_TEAM:
                    runNewTeamCreatorProccess();
                    break;
                case JOIN_TEAM:
                    runUserToTeamJoinProcess();
                    break;
                case LEAVE_TEAM:
                    runUserLeaveTeamProcess();
                    break;
                case EXIT:
                    userDecidedToExitController = true;

            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    private void runNewTeamCreatorProccess() throws SQLException {
        if (optionalTeam.isPresent()) view.showThatUserIsAlreadyInTeam();
        else {
            String newTeamName = view.getUserInput("Name of new team: ");
            if (newTeamName.isEmpty()) {
                view.showWrongInput();
                view.continuePrompt();
            } else if (!teamsDAO.checkIfTeamExists(newTeamName)) {
                Team newTeam = new Team(newTeamName);

                teamsDAO.createTeam(newTeam);
                teamsDAO.addUserToTeam(codecooler, newTeam);
                optionalTeam = Optional.of(newTeam);

                view.showNewTeamHaveBeenCreatedSuccessfully(newTeamName);
            } else {
                view.showThatTeamAlreadyExists();
            }
        }
    }

    private void runUserToTeamJoinProcess() {
        if (optionalTeam.isPresent()) view.showThatUserIsAlreadyInTeam();
        else {
            List<String> teams = getTeamsAsStringsList();
            if (teams.isEmpty()) {
                view.showNoTeamsInDatabase();
                return;
            }
            view.showTeamsList(teams);
            Optional<Team> selectedTeam = Optional.ofNullable(teamsDAO.getTeam(getTeamIDFromUser()));
            if (!selectedTeam.isPresent()) view.showWrongInput();
            else {
                optionalTeam = selectedTeam;
                teamsDAO.addUserToTeam(codecooler, selectedTeam.get());
                view.showUserHavenAddedToTeamSuccessfully(optionalTeam.get().getName());
            }

        }

    }

    private void runUserLeaveTeamProcess() {
        if (!optionalTeam.isPresent()) view.showThatUserIsNotInTeam();
        else {
            teamsDAO.removeUserFromTeam(codecooler);
            view.showUserHavenBeenRemovedFromTeamSuccessfully(optionalTeam.get().getName());
            optionalTeam = Optional.empty();
        }
    }

    private List<String> getTeamsAsStringsList() {
        return teamsDAO.getAllTeams().stream().map(Team::toString).collect(Collectors.toList());
    }

    private int getTeamIDFromUser() {
        try {
            return Integer.valueOf(view.getUserInput("Type team ID: "));
        } catch (Exception e) {
            view.showWrongInput();
        }
        return -1;
    }
}
