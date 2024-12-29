package edu.pw.ii.pap.z29.controller;

import lombok.Data;
import lombok.experimental.StandardException;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.view.GUIHelper;
import edu.pw.ii.pap.z29.view.ProfilePane;
import edu.pw.ii.pap.z29.model.primitives.Score;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;


public class ProfileController {
    MainController mainController;

    public ProfileController(MainController mainController) {
        this.mainController = mainController;
    }

    public UserData readUserData() throws UserDataException {
        var user_data = new UserData();
        user_data.setUser(mainController.getLoginController().getCurrentUser());
        var user_id = user_data.getUser().getUserId();
        try {
            var loginPassword = mainController.getLoginPasswords().read(user_id).orElseThrow(() -> 
                new IllegalArgumentException("No login password found for user ID: " + user_id)
            );
            user_data.password = loginPassword.getPassword();
            user_data.scores = mainController.getScores().readAllScores(user_id);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
            throw new UserDataException(e);
        } catch (IllegalArgumentException e) {
            throw new UserDataException(e);
        }
        return user_data;
    }

    public void wantToDeleteAccount() {
        boolean really_delete = assureAboutDeletion();
        if (really_delete) {
            boolean deleted = false;
            boolean sql_error = false;
            try {
                var user = mainController.getLoginController().getCurrentUser();
                deleted =  mainController.getUsers().delete(user.getUserId());
            } catch (SQLException e) {
                mainController.getSqlLogger().log(e);
                sql_error = true;
            }
            if (sql_error || !deleted)
                GUIHelper.showError(mainController.getGui().getFrame(), "Unsuccessfull deletion.");
            else
                GUIHelper.showError(mainController.getGui().getFrame(), "Account deleted successfully.");
            mainController.gui.showPane(GUI.Pane.Login);
        }
    }

    public void wantToUpdateUsername(Username username) {
        if (updateUsername(username)) {
            var profilePane = (ProfilePane)mainController.getGui().getPane(GUI.Pane.Profile);
            profilePane.updateUserData();
        } else
            GUIHelper.showError(mainController.getGui().getFrame(), "Haven't updated the username.");
    }

    private boolean updateUsername(Username newUsername) {
        var user = mainController.getLoginController().getCurrentUser();
        user.setUsername(newUsername);
        var did_update = false;
        try {
            did_update = mainController.getUsers().update(user.getUserId(), user);
        }
        catch (SQLException e){
            mainController.getSqlLogger().log(e);
        }
        return did_update;
    }

    public boolean updatePassword(Password newPassword) {
        var user_id = mainController.getLoginController().getCurrentUser().getUserId();
        var did_update = false;
        try {
            var loginPassword = mainController.getLoginPasswords().read(user_id).get();
            loginPassword.setPassword(newPassword);
            did_update = mainController.getLoginPasswords().update(loginPassword);
        }
        catch (SQLException e){
            mainController.getSqlLogger().log(e);
        }
        return did_update;
    }

    private boolean assureAboutDeletion() {
        return JOptionPane.showConfirmDialog(
            mainController.getGui().getFrame(),
            "Are you sure you want to delete your account?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        ) == JOptionPane.YES_OPTION;
    }

    public void wantToSeeFriends() {
        
    }

    @Data
    static public class UserData {
        private User user;
        private Password password;
        private List<Score> scores;

        public UserData() {}

        public UserData(User user, Password password, List<Score> scores) {
            this.user = user;
            this.password = password;
            this.scores = scores;
        }
    }

    @StandardException
    static public class UserDataException extends RuntimeException {}
}
