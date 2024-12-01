package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import edu.pw.ii.pap.z29.exception.NotLoggedInException;


public class LoginController {
    MainController mainController;
    private Integer currentUserId = null;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void checkLogin(String login, String password) {
        boolean correct = false;
        try {
            correct = mainController.loginPasswords.checkCredentials(
                login, password);
            currentUserId = 0;  // TODO: checkCredentials must return user_id;
        } catch (SQLException e) {
            mainController.sqlLogger.log(e);
        }
        if (correct) {
            mainController.gui.disposeOfLoginFrame();
            mainController.gui.showMainFrame();
        } else {
            JOptionPane.showMessageDialog(
                mainController.gui.getLoginFrame(), "Try again!");
        }
    }

    public int getCurrentUserId() {
        if (currentUserId == null)
            throw new NotLoggedInException();
        return currentUserId;
    }

    public void wantToRegister() {
        mainController.gui.disposeOfLoginFrame();
        mainController.gui.showRegisterFrame();
    }

    public void wantToLogout() {
        mainController.gui.disposeOfMainFrame();
        mainController.gui.showLoginFrame();
        currentUserId = null;
    }

    public void seeProfile() {
        mainController.gui.disposeOfMainFrame();
        mainController.gui.showProfileFrame();
    }
}
