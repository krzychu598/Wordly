package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

import javax.swing.JOptionPane;
import edu.pw.ii.pap.z29.exception.NotLoggedInException;

import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.view.GUI;
import lombok.Data;

@Data
public class LoginController {
    MainController mainController;
    private User currentUser = null;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void checkLogin(String login, String password) {
        if (loginUser(login, password)) {
            mainController.gui.showPane(GUI.Pane.Home);
        } else {
            JOptionPane.showMessageDialog(mainController.gui.getFrame(), "Try again!");
        }
    }

    public synchronized User getCurrentUser() {
        if (currentUser == null)
            throw new NotLoggedInException();
        return currentUser;
    }

    public void wantToRegister() {
        mainController.gui.showPane(GUI.Pane.Register);
    }

    public void wantToLogout() {
        currentUser = null;
        mainController.gui.showPane(GUI.Pane.Login);
    }

    public void seeProfile() {
        mainController.gui.showPane(GUI.Pane.Profile);
    }

    public void wantToLogin() {
        mainController.gui.showPane(GUI.Pane.Login);
    }

    private boolean loginUser(String login, String password) {
        boolean is_correct = false;
        try {
            Optional<User> userOpt = mainController.users.readByUsername(login);
            if (userOpt.isPresent()) {
                var user = userOpt.get();
                is_correct = mainController.loginPasswords.checkCredentials(user.getUserId(), password);
                synchronized(this) {
                    currentUser = user;
                }
            }
        } catch (SQLException e) {
            mainController.sqlLogger.log(e);
        }
        return is_correct;
    }
}
