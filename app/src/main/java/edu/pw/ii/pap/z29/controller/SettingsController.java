package edu.pw.ii.pap.z29.controller;

import edu.pw.ii.pap.z29.view.GUI;

public class SettingsController {
    MainController mainController;

    public SettingsController(MainController mainController) {
        this.mainController = mainController;
    }

    public void goBack() {
        mainController.getGui().showPane(GUI.Pane.Home);
    }
}
