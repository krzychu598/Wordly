package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LoginController {
    MainController mainController;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void checkLogin(String login, String password) {
        boolean correct = false;
        try {
            correct = mainController.loginPasswords.checkCredentials(
                login, password);
        } catch (SQLException e) {
            mainController.sqlLogger.log(e);
        }
        if (correct) {
            JOptionPane.showMessageDialog(
                mainController.gui.getLoginFrame(), "Welcome!");
        } else {
            JOptionPane.showMessageDialog(
                mainController.gui.getLoginFrame(), "Try again!");
        }
    }
}
