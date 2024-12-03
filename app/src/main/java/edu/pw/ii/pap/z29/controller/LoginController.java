package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import java.util.Optional;

import javax.swing.JOptionPane;
import edu.pw.ii.pap.z29.exception.NotLoggedInException;


import edu.pw.ii.pap.z29.model.primitives.User;
import lombok.Data;

@Data
public class LoginController {
    MainController mainController;
    private User currentUser = null;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void checkLogin(String login, String password) {
        boolean correct = false;
        try {
            Optional<User> userOpt = mainController.users.readByUsername(login);
            if (userOpt.isPresent()) {
                var user = userOpt.get();
                correct = mainController.loginPasswords.checkCredentials(user.getUserId(), password);
                currentUser = user;
            }
        } catch (SQLException e) {
            mainController.sqlLogger.log(e);
        }
        if (correct) {
            mainController.gui.disposeOfLoginFrame();
            mainController.gui.showMainFrame();
        } else {
            JOptionPane.showMessageDialog(mainController.gui.getLoginFrame(), "Try again!");
        }
    }

    public User getCurrentUser() {
        if (currentUser == null)
            throw new NotLoggedInException();
        return currentUser;
    }

    public void wantToRegister() {
        mainController.gui.showRegisterFrame();
    }

    public void wantToLogout() {
        currentUser = null;
        mainController.gui.showLoginFrame();
    }

    public void seeProfile() {
        mainController.gui.showProfileFrame();
    }

    public void wantToLogin() {
        mainController.gui.showLoginFrame();
    }
}
