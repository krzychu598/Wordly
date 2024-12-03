package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;

import edu.pw.ii.pap.z29.controller.MainController;



public class MainFrame extends JFrame {
    private static final Color MY_PANEL_COLOR = Color.decode("#406080");
    JLabel titleLabel;
    JTextField usernameField;
    JPasswordField passwordField;

    GUI gui;

    public MainFrame(GUI gui) {
        super("Main");
        this.gui = gui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(GUI.MAIN_COLOR);
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
        centralPanel.setOpaque(false);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);
    }

    private JMenu createProfileMenu() {
        var menu = new JMenu("Profile");

        var logoutAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.dispose();
                gui.getMainController().getLoginController().wantToLogout();
            }
        };
        logoutAction.putValue(Action.NAME, "Log Out");
        menu.add(logoutAction);

        var seeProfileAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.dispose();
                gui.getMainController().getLoginController().seeProfile();
            }
        };
        seeProfileAction.putValue(Action.NAME, "See Profile");
        menu.add(seeProfileAction);

        return menu;
    }
}
