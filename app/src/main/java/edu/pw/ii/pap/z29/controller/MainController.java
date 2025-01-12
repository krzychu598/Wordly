package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import edu.pw.ii.pap.z29.Database;
import edu.pw.ii.pap.z29.view.GUI;
import lombok.Getter;
import lombok.experimental.StandardException;
import lombok.AccessLevel;
import edu.pw.ii.pap.z29.model.SQLLogger;
import edu.pw.ii.pap.z29.model.ScoresTable;
import edu.pw.ii.pap.z29.model.UsersTable;
import edu.pw.ii.pap.z29.model.primitives.User;
import java.util.Optional;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.model.primitives.LoginPassword;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.FriendshipsTable;
import edu.pw.ii.pap.z29.model.LoginPasswordTable;


@Getter
public class MainController {
    LoginController loginController;
    GameController gameController;
    ProfileController profileController;
    FriendsController friendsController;
    GameSummaryController gameSummaryController;
    GUI gui;
    @Getter(AccessLevel.PACKAGE) SQLLogger sqlLogger = new SQLLogger();
    @Getter(AccessLevel.NONE) Database database = new Database();
    @Getter(AccessLevel.PACKAGE) UsersTable users;
    @Getter(AccessLevel.PACKAGE) LoginPasswordTable loginPasswords;
    @Getter(AccessLevel.PACKAGE) ScoresTable scores;
    @Getter(AccessLevel.PACKAGE) FriendshipsTable friendships;

    public void run() {
        profileController = new ProfileController(this);
        loginController = new LoginController(this);
        friendsController = new FriendsController(this);
        try {
            var conn = database.getConnection();
            users = new UsersTable(conn);
            loginPasswords = new LoginPasswordTable(conn);
            scores = new ScoresTable(conn);
            friendships = new FriendshipsTable(conn);
        } catch (SQLException e) {
            sqlLogger.log(e);
        }
        gui = new GUI(this);
        gui.run();
  
    }

    public void newGame(int wordLength){
        gameController = new GameController(this, wordLength);
        gui.showPane(GUI.Pane.Game);
    }

    public void newSummary(int score){
        gameSummaryController = new GameSummaryController(this, score);
        gui.showPane(GUI.Pane.GameSummary);

    }
    public boolean addUser(Username username, Password password) {
        try {
            User user = new User(username);
            int userId = users.create(user);
            if (userId > 0) {
                user.setUserId(userId);
                LoginPassword loginPassword = new LoginPassword(userId, password);
                loginPasswords.create(loginPassword);
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    @StandardException
    static public class UserDataException extends RuntimeException {}
}
