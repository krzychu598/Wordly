package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import edu.pw.ii.pap.z29.controller.MainController;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.CardPane.PaneInitException;
import lombok.Getter;
import lombok.Setter;


public class GUI {
    protected static final Color BLACK = Color.decode("#000000");
    protected static final Color MAIN_COLOR = Color.decode("#101820");
    protected static final Color SECONDARY_COLOR = Color.decode("#FEE715");
    protected static final Font PLAIN_FONT = new Font("Dialog", Font.PLAIN, 20);
    protected static final Color GREEN = Color.decode("#008000");
    protected static final Color YELLOW = Color.decode("#FFFF00");
    protected static final Color ORANGE = Color.decode("#FFBF00");
    protected static final Color WHITE = Color.decode("#F0F8FF");
    protected static final Color BLUE = Color.decode("#0077B3");

    public enum Pane {
        Friends,
        Game,
        GameHistory,
        Home,
        Login,
        Profile,
        Register,
        Settings,
        GameSummary,
        FriendProfilePane
    }
    @Getter @Setter private boolean darkMode = true;
    @Getter @Setter private boolean privateProfile = true;
    @Getter private MainController mainController;
    private HashMap<Pane, CardPane> panes = new HashMap<Pane, CardPane>();
    Pane currentPane;
    @Getter MainFrame frame;

    public GUI(MainController mainController) {
        this.mainController = mainController;
        frame = new MainFrame(this);
        addPane(Pane.Friends, new FriendsPane(this));
        addPane(Pane.Game, new GamePane(this));
        addPane(Pane.GameHistory, new GameHistoryPane(this));
        addPane(Pane.Home, new HomePane(this));
        addPane(Pane.Login, new LoginPane(this));
        addPane(Pane.Profile, new ProfilePane(this));
        addPane(Pane.Register, new RegisterPane(this));
        addPane(Pane.Settings, new SettingsPane(this));
        addPane(Pane.GameSummary, new GameSummaryPane(this));
        addPane(Pane.FriendProfilePane, new FriendProfilePane(this));
    }

    public MainController getMainController() {
        return mainController;
    }

    public JPanel getPane(Pane pane) {
        return panes.get(pane);
    }

    public synchronized Pane getCurrentPane() {
        return currentPane;
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public synchronized boolean showPane(Pane pane) throws PaneInitException {
        boolean shown = frame.showPane(panes.get(pane));
        if (shown)
            currentPane = pane;
        return shown;
    }

    private void addPane(Pane name, CardPane pane) {
        panes.put(name, pane);
        frame.addPane(pane);
    }

    public void updateTheme() {
        ((ProfilePane) panes.get(Pane.Profile)).setDarkMode(darkMode);
        ((RegisterPane) panes.get(Pane.Register)).setDarkMode(darkMode);
        ((LoginPane) panes.get(Pane.Login)).setDarkMode(darkMode);
        ((HomePane) panes.get(Pane.Home)).setDarkMode(darkMode);
        ((GamePane) panes.get(Pane.Game)).setDarkMode(darkMode);
        ((FriendsPane) panes.get(Pane.Friends)).setDarkMode(darkMode);
        ((GameHistoryPane) panes.get(Pane.GameHistory)).setDarkMode(darkMode);
        ((SettingsPane) panes.get(Pane.Settings)).setDarkMode(darkMode);
        // ((GameSummaryPane) panes.get(Pane.GameSummary)).setDarkMode(darkMode);
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    private void createAndShowGUI() {
        frame.pack();
        frame.setVisible(true);
        frame.showPane(panes.get(Pane.Login));
    }
}
