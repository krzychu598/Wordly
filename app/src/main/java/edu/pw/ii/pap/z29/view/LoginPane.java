package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import javax.swing.*;

import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.FormPanel;
import edu.pw.ii.pap.z29.model.primitives.Password;

import java.lang.Thread;
import java.util.Map;



public class LoginPane extends CardPane {
    JLabel titleLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    GUI gui;

    public LoginPane(GUI gui) {
        this.gui = gui;
        setName("LoginPane");
        setBackground(GUI.MAIN_COLOR);
        setLayout(new GridBagLayout());
        addGuiParts();
    }

    @Override public void init() {}

    @Override public void cleanup() {
        usernameField.setText("");
        passwordField.setText("");
    }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setOpaque(false);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);

        var strut = (JComponent)Box.createHorizontalStrut(400);
        strut.setAlignmentX(LEFT_ALIGNMENT);
        centralPanel.add(strut);

        var titlePanel = GUIHelper.createContainerPanel();
        titleLabel = GUIHelper.createDefaultLabel("The Wordle Game", 40);
        titlePanel.add(titleLabel);
        centralPanel.add(titlePanel);

        centralPanel.add(Box.createVerticalStrut(30));

        usernameField = GUIHelper.formatTextField(
            new JTextField(), GUI.SECONDARY_COLOR, GUI.MAIN_COLOR, GUI.PLAIN_FONT);
        var usernamePanel = new FormPanel(usernameField, "Username", GUI.MAIN_COLOR);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(usernamePanel);

        centralPanel.add(Box.createVerticalStrut(20));

        passwordField = (JPasswordField)GUIHelper.formatTextField(
            new JPasswordField(), GUI.SECONDARY_COLOR, GUI.MAIN_COLOR, GUI.PLAIN_FONT);
        var passwordPanel = new FormPanel(passwordField, "Password", GUI.MAIN_COLOR);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(passwordPanel);

        centralPanel.add(Box.createVerticalStrut(40));

        var buttonPanel = GUIHelper.createContainerPanel(new GridLayout());
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loginButton.setBackground(GUI.SECONDARY_COLOR);
        loginButton.setForeground(GUI.MAIN_COLOR);
        loginButton.addActionListener(
            (ActionEvent e) -> {
                var username = new Username(usernamePanel.field.getText());
                var password = new Password(new String(passwordField.getPassword()));
                (new Thread(() -> {
                    gui.getMainController().getLoginController().checkLogin(username, password);
                })).start();;
            });
        buttonPanel.add(loginButton);
        centralPanel.add(buttonPanel);

        centralPanel.add(Box.createVerticalStrut(20));

        var registerPanel = GUIHelper.createContainerPanel();
        var registerLabel = new JLabel("<HTML><U>Don't have an account? Register</U></HTML>");
        registerLabel.setForeground(GUI.SECONDARY_COLOR);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.getMainController().getLoginController().wantToRegister();
            }
        });
        registerPanel.add(registerLabel);
        centralPanel.add(registerPanel);
    }

    public void setDarkMode(boolean darkMode) {
        if (darkMode) {
            setBackground(GUI.MAIN_COLOR);
            setAllForeground(this, GUI.SECONDARY_COLOR, GUI.SECONDARY_COLOR, java.awt.Color.BLACK);
        } else {
            setBackground(java.awt.Color.WHITE);
            setAllForeground(this, GUI.BLUE, GUI.BLUE, java.awt.Color.BLACK);
        }
        revalidate();
        repaint();
    }

    private void setAllForeground(java.awt.Container container,
                                  java.awt.Color defaultColor,
                                  java.awt.Color buttonBgColor,
                                  java.awt.Color buttonFontColor) {
        for (java.awt.Component c : container.getComponents()) {
            if (c instanceof JButton) {
                c.setBackground(buttonBgColor);
                c.setForeground(buttonFontColor);
            } else {
                c.setForeground(defaultColor);
            }
            if (c instanceof java.awt.Container) {
                setAllForeground((java.awt.Container) c, defaultColor, buttonBgColor, buttonFontColor);
            }
        }
    }
}
