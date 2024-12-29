package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.locks.Lock;

import javax.swing.JOptionPane;
import edu.pw.ii.pap.z29.exception.NotLoggedInException;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.view.CardPane.PaneInitException;


public class LoginController {
    MainController mainController;
    private User currentUser = null;

    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }

    public void checkLogin(Username username, Password password) {
        if (loginUser(username, password)) {
            mainController.gui.showPane(GUI.Pane.Home);
        } else {
            JOptionPane.showMessageDialog(mainController.gui.getFrame(), "Try again!");
        }
    }

    public synchronized User getCurrentUser() {
        if (currentUser == null)
            throw new NotLoggedInException();
        return currentUser.toBuilder().build();
    }

    public void wantToRegister() {
        mainController.gui.showPane(GUI.Pane.Register);
    }

    public void wantToLogout() {
        currentUser = null;
        mainController.gui.showPane(GUI.Pane.Login);
    }

    public void seeProfile() {
        try {
            mainController.gui.showPane(GUI.Pane.Profile);
        } catch (PaneInitException e) {
            JOptionPane.showMessageDialog(mainController.gui.getFrame(), "Couldn't show profile.");
        }
    }

    public void wantToLogin() {
        mainController.gui.showPane(GUI.Pane.Login);
    }

    private boolean loginUser(Username username, Password password) {
        boolean is_correct = false;
        try {
            Optional<User> userOpt = mainController.users.read(username);
            if (userOpt.isPresent()) {
                var user = userOpt.get();
                var login_passwd = mainController.loginPasswords.read(user.getUserId()).get();
                is_correct = login_passwd.getPassword().equals(password);
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
