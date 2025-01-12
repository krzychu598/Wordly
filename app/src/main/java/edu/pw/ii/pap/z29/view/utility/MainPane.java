package edu.pw.ii.pap.z29.view.utility;

import java.awt.*;
import javax.swing.*;

import edu.pw.ii.pap.z29.view.utility.CardPane.PaneInitException;


public class MainPane extends JPanel {
    CardLayout layout = new CardLayout();
    CardPane currentPane;
    
    public MainPane() {
        setLayout(layout);
        setOpaque(false);
        add(new JPanel(), "null");
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
            layout.show(this, pane.getName());
            shown = true;
        }
        return shown;
    }

    public synchronized void clear() {
        if (currentPane != null)
            currentPane.cleanup();
        layout.show(this, "null");
        currentPane = null;
    }

    public synchronized boolean refresh() {
        var pane = currentPane;
        clear();
        return showPane(pane);
    }
}
