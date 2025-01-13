package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;

import edu.pw.ii.pap.z29.view.GUI;

public class SettingsController {
    MainController mainController;

    public SettingsController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPrivateProfile(boolean privProfile) {
        try {
            if (!mainController.getSettings().update(mainController.getUserId(), privProfile))
                mainController.getSettings().create(mainController.getUserId(), privProfile);
        } catch (SQLException e) {
            mainController.getSqlLogger().log(e);
        }
    }

    public void goBack() {
        mainController.getGui().showPane(GUI.Pane.Home);
    }
}
