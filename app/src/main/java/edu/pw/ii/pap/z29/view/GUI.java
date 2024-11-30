package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.pw.ii.pap.z29.controller.MainController;


public class GUI {

    private MainController mainController;

    public GUI(MainController mainController) {
        this.mainController = mainController;
    }

    private void createAndShowGUI() {
        final var frame = new LoginFrame(mainController);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
