package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import edu.pw.ii.pap.z29.controller.MainController;


public class GUI {
    protected static final Color BLACK = Color.decode("#000000");
    protected static final Color MAIN_COLOR = Color.decode("#101820");
    protected static final Color SECONDARY_COLOR = Color.decode("#FEE715");
    protected static final Font PLAIN_FONT = new Font("Dialog", Font.PLAIN, 20);
    protected static final Color GREEN = Color.decode("#008000");
    protected static final Color YELLOW = Color.decode("#FFFF00");

    public enum Pane {
        Game,
        Home,
        Login,
        Profile,
        Register,
    }

    private MainController mainController;
    private HashMap<Pane, CardPane> panes = new HashMap<Pane, CardPane>();
    Pane currentPane;
    MainFrame frame;
    
    public GUI(MainController mainController) {
        this.mainController = mainController;
        frame = new MainFrame(this);
        addPane(Pane.Game, new GamePane(this));
        addPane(Pane.Home, new HomePane(this));
        addPane(Pane.Login, new LoginPane(this));
        addPane(Pane.Profile, new ProfilePane(this));
        addPane(Pane.Register, new RegisterPane(this));
    }    
    
    public MainController getMainController() {
        return mainController;
    }

    public JPanel getPane(Pane pane) {
        return panes.get(pane);
    }

    public JFrame getFrame() {
        return frame;
    }

    public synchronized Pane getCurrentPane() {
        return currentPane;
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }    
    
    public synchronized void showPane(Pane pane) {
        if (frame.showPane(panes.get(pane)))
            currentPane = pane;
    }
    
    private void addPane(Pane name, CardPane pane) {
        panes.put(name, pane);
        frame.addPane(pane);
    }
    
    private void createAndShowGUI() {
        frame.pack();
        frame.setVisible(true);
        frame.showPane(panes.get(Pane.Login));
    }
}
