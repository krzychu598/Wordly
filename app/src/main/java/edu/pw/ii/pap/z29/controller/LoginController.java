package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import java.util.Optional;

import javax.swing.JOptionPane;

import edu.pw.ii.pap.z29.model.primitives.User;
import lombok.Data;

@Data
public class LoginController {
    MainController mainController;
    int currentUserId;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void checkLogin(String login, String password) {
        boolean correct = false;
        try {
            Optional<User> userOpt = mainController.users.readByUsername(login);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                correct = mainController.loginPasswords.checkCredentials(user.getUserId(), password);
                currentUserId = user.getUserId();
            }
        } catch (SQLException e) {
            mainController.sqlLogger.log(e);
        }
        if (correct) {
            JOptionPane.showMessageDialog(mainController.gui.getLoginFrame(), "Welcome!");
        } else {
            JOptionPane.showMessageDialog(mainController.gui.getLoginFrame(), "Try again!");
        }
    }

    public void wantToRegister() {
        mainController.gui.showRegisterFrame();
    }

    public void wantToLogin() {
        mainController.gui.showLoginFrame();
    }
}
