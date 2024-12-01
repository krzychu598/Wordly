package edu.pw.ii.pap.z29.view;

import java.awt.*;
import javax.swing.*;
import edu.pw.ii.pap.z29.controller.MainController;



public class MainFrame extends JFrame {
    public static final Color BLACK = Color.decode("#000000");
    public static final Color MAIN_COLOR = Color.decode("#101820");
    public static final Color TEXT_COLOR = Color.decode("#FEE715");
    public static final Color MY_PANEL_COLOR = Color.decode("#406080");
    public static final Font PLAIN_FONT = new Font("Dialog", Font.PLAIN, 20);
    JLabel titleLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    MainController mainController;

    public MainFrame(MainController mainController) {
        super("Main");
        this.mainController = mainController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BLACK);
        getContentPane().setLayout(new GridBagLayout());
        addGuiParts();
        pack();
        setVisible(true);
    }

    private void addGuiParts() {
        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        var profileMenu = createProfileMenu();
        menuBar.add(profileMenu);

        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(MAIN_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);
    }

    private JMenu createProfileMenu() {
        var menu = new JMenu("Profile");
        return menu;
    }
}
