package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Thread;



public class LoginPane extends CardPane {
    JLabel titleLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    GUI gui;
    int i = 0;

    public LoginPane(GUI gui) {
        this.gui = gui;
        setName("LoginPane");
        setLayout(new GridBagLayout());
        addGuiParts();
    }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(GUI.MAIN_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);

        titleLabel = GUI.createTitleLabel(40);
        centralPanel.add(titleLabel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        usernameField = GUIHelper.formatTextField(
            new JTextField(), GUI.SECONDARY_COLOR, GUI.MAIN_COLOR, GUI.PLAIN_FONT);
        var usernamePanel = new FormPanel(usernameField, "Username", GUI.MAIN_COLOR);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(usernamePanel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        passwordField = (JPasswordField)GUIHelper.formatTextField(
            new JPasswordField(), GUI.SECONDARY_COLOR, GUI.MAIN_COLOR, GUI.PLAIN_FONT);
        var passwordPanel = new FormPanel(passwordField, "Password", GUI.MAIN_COLOR);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(passwordPanel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.setOpaque(false);
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loginButton.setBackground(GUI.SECONDARY_COLOR);
        loginButton.setForeground(GUI.MAIN_COLOR);
        loginButton.addActionListener(
            (ActionEvent e) -> {
                String username = usernamePanel.field.getText();
                String password = new String(passwordField.getPassword());
                (new Thread(() -> {
                    gui.getMainController().getLoginController().checkLogin(username, password);
                })).start();;
            });
        buttonPanel.add(loginButton);
        centralPanel.add(buttonPanel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        var registerPanel = new JPanel(new GridLayout());
        registerPanel.setOpaque(false);
        registerPanel.setAlignmentX(LEFT_ALIGNMENT);
        registerPanel.setMaximumSize(titleLabel.getPreferredSize());
        var innerRegisterPanel = new JPanel();
        innerRegisterPanel.setLayout(new BoxLayout(innerRegisterPanel, BoxLayout.PAGE_AXIS));
        innerRegisterPanel.setOpaque(false);
        JLabel registerLabel = new JLabel("Don't have an account? Register");
        registerLabel.setForeground(GUI.SECONDARY_COLOR);
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                gui.getMainController().getLoginController().wantToRegister();
            }
        });
        registerLabel.setAlignmentX(CENTER_ALIGNMENT);
        innerRegisterPanel.add(registerLabel);
        registerPanel.add(innerRegisterPanel);
        centralPanel.add(registerPanel);
    }

    synchronized int nexti() {
        return i++;
    }

    @Override void init() {}

    @Override void cleanup() {}
}
