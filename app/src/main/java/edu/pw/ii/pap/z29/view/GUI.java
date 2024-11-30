package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class GUI {
    private void createAndShowGUI() {
        final var frame = new LoginFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        frame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
