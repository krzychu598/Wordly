package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import edu.pw.ii.pap.z29.Database;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.model.SQLLogger;
import edu.pw.ii.pap.z29.model.ScoresTable;
import edu.pw.ii.pap.z29.model.UsersTable;
import edu.pw.ii.pap.z29.model.primitives.User;
import java.util.Optional;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.model.primitives.LoginPassword;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.LoginPasswordTable;
import lombok.Data;

@Data
public class MainController {
    LoginController loginController;
    GameController gameController;
    GUI gui;

    SQLLogger sqlLogger = new SQLLogger();
    Database database = new Database();
    UsersTable users;
    LoginPasswordTable loginPasswords;
    ScoresTable scores;

    public void run() {
        loginController = new LoginController(this);
        gui = new GUI(this);
        try {
            var conn = database.getConnection();
            users = new UsersTable(conn);
            loginPasswords = new LoginPasswordTable(conn);
            scores = new ScoresTable(conn);
        } catch (SQLException e) {
            sqlLogger.log(e);
        }
        gui.run();
    }

    public LoginController getLoginController() {
        return loginController;
    }
    public void newGame(int wordLength, boolean definition){
        gameController = new GameController(this, wordLength, definition);
        gui.disposeOfMainFrame();
        gui.showGameFrame();
    }
    public GameController getGamController() {
        return gameController;
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
        }   catch (SQLException e) {
            sqlLogger.log(e);
            return false;
        }
    }


    public boolean checkCredentials(String username, String password) {
        try {
            Optional<User> userOpt = users.readByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                return loginPasswords.checkCredentials(user.getUserId(), password);
            }
            return false;
        } catch (SQLException e) {
            sqlLogger.log(e);
            return false;
        }
    }

}
