package edu.pw.ii.pap.z29.view;

import java.awt.*;
import javax.swing.*;

import edu.pw.ii.pap.z29.view.CardPane.PaneInitException;


public class MainFrame extends JFrame {
    GUI gui;
    CardLayout layout = new CardLayout();
    CardPane currentPane;
    
    public MainFrame(GUI gui) {
        super("Wordle");
        this.gui = gui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(layout);
    }

    public synchronized CardPane getCurrentPane() {
        return currentPane;
    }

    public synchronized void addPane(CardPane pane) {
        add(pane, pane.getName());
    }

    public synchronized boolean showPane(CardPane pane) throws PaneInitException {
        boolean shown = false;
        if (currentPane != pane) {
            if (currentPane != null)
                currentPane.cleanup();
            currentPane = pane;
            currentPane.init();
            layout.show(getContentPane(), pane.getName());
            shown = true;
        }
        return shown;
    }
}
