package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.pw.ii.pap.z29.controller.MainController;


public class GUI {

    private MainController mainController;
    private LoginFrame loginFrame;

    public GUI(MainController mainController) {
        this.mainController = mainController;
    }

    private void createAndShowGUI() {
        loginFrame = new LoginFrame(mainController);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        loginFrame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }
}
