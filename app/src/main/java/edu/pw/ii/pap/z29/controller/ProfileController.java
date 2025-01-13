package edu.pw.ii.pap.z29.controller;

import lombok.Data;
import edu.pw.ii.pap.z29.model.primitives.Friendship;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.view.GUIHelper;
import edu.pw.ii.pap.z29.view.ProfilePane;
import edu.pw.ii.pap.z29.model.primitives.Score;
import edu.pw.ii.pap.z29.model.primitives.Level;
import edu.pw.ii.pap.z29.exception.UserDataException;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

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
            var loginPassword = mainController.getLoginPasswords().read(user_id).get();
            user_data.password = loginPassword.getPassword();
            user_data.scores = mainController.getScores().readAllScores(user_id);
            user_data.level = mainController.getLevels().readByScore(
                Score.total_score(user_data.scores)).get();
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
            throw new UserDataException(e);
        } catch (NoSuchElementException e) {
            throw new UserDataException(e);
        }
        return user_data;
    }
    public int maxScore() {
        int userID = mainController.getUserId();
        var scores = readScores(userID);
        var max_score = scores.size() != 0 ?
            Collections.max(scores, (Score s1, Score s2) -> {
                if (s1.getScore() == s2.getScore())
                    return 0;
                return s1.getScore() < s2.getScore() ? -1 : 1;
            }).getScore() : 0;
            return max_score;
    }
    public List<Friendship> readFriendships() throws UserDataException {
        int user_id = mainController.getUserId();
        List<Friendship> friendships;
        try {
            friendships = mainController.getFriendships().read_friends(user_id);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
            throw new UserDataException(e);
        }
        return friendships;
    }

    public void goBack() {
        mainController.getGui().showPane(GUI.Pane.Home);
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
        var user_id = mainController.getUserId();
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
        mainController.getGui().showPane(GUI.Pane.Friends);
    }

    public int getTotalScore() {
        int user_id = mainController.getUserId();
        int score = 0;
        try {
            score =  mainController.getScores().readTotalScore(user_id);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
        }
        return score;
    }

    public Level getLevel() {
        var score = getTotalScore();
        Level level = null;
        try {
            var level_opt = mainController.getLevels().readByScore(score);
            if (level_opt.isEmpty())
                level = mainController.getLevels().readHighestLevel().get();
            else
                level = level_opt.get();
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
            throw new UserDataException(e);
        }
        return level;
    
    }

    public void wantToSeeGameHistory() {
        mainController.getGui().showPane(GUI.Pane.GameHistory);
    }

    public List<Score> readScores(int user_id) {
        List<Score> scores;
        try {
            scores = mainController.getScores().readAllScores(user_id);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
            scores = List.of();
        }
        return scores;
    }

    @Data
    static public class UserData {
        private User user;
        private Password password;
        private List<Score> scores;
        private Level level;

        public UserData() {}

        public UserData(User user, Password password, List<Score> scores) {
            this.user = user;
            this.password = password;
            this.scores = scores;
        }
    }
}
