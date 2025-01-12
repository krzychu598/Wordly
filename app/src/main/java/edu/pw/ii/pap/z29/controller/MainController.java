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
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.model.primitives.LoginPassword;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.FriendshipsTable;
import edu.pw.ii.pap.z29.model.LevelsTable;
import edu.pw.ii.pap.z29.model.LoginPasswordTable;


@Getter
public class MainController {
    LoginController loginController;
    GameController gameController;
    ProfileController profileController;
    FriendsController friendsController;
    GUI gui;
    @Getter(AccessLevel.PACKAGE) SQLLogger sqlLogger = new SQLLogger();
    @Getter(AccessLevel.NONE) Database database = new Database();
    @Getter(AccessLevel.PACKAGE) UsersTable users;
    @Getter(AccessLevel.PACKAGE) LoginPasswordTable loginPasswords;
    @Getter(AccessLevel.PACKAGE) ScoresTable scores;
    @Getter(AccessLevel.PACKAGE) FriendshipsTable friendships;
    @Getter(AccessLevel.PACKAGE) LevelsTable levels;

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
            levels = new LevelsTable(conn);
        } catch (SQLException e) {
            sqlLogger.log(e);
        }
        gui = new GUI(this);
        gui.run();
        //gui.skipLogin();
    }

    public void newGame(int wordLength){
        gameController = new GameController(this, wordLength);
        //System.out.println("created controller");
        gui.showPane(GUI.Pane.Game);
        //System.out.println("shown frame");
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

    int getUserId() {
        return loginController.getCurrentUser().getUserId();
    }

    @StandardException
    static public class UserDataException extends RuntimeException {}
}
