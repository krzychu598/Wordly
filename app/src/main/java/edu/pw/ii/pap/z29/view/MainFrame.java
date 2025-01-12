package edu.pw.ii.pap.z29.view;

import java.awt.*;
import javax.swing.*;

import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.MainPane;
import edu.pw.ii.pap.z29.view.utility.CardPane.PaneInitException;


public class MainFrame extends JFrame {
    GUI gui;
    MainPane contentPane;
    
    public MainFrame(GUI gui) {
        super("Wordle");
        this.gui = gui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.contentPane = new MainPane();
        setContentPane(contentPane);
    }

    public synchronized CardPane getCurrentPane() {
        return contentPane.getCurrentPane();
    }

    public synchronized void addPane(CardPane pane) {
        contentPane.add(pane, pane.getName());
    }

    public synchronized boolean showPane(CardPane pane) throws PaneInitException {
        var success = contentPane.showPane(pane);
        setMinimumSize(getSize());
        pack();
        return success;
    }

    public synchronized void clear() {
        contentPane.clear();
    }

    public boolean refresh() {
        return contentPane.refresh();
    }
}
