package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.FormPanel;


public class RegisterPane extends CardPane {
    JTextField usernameField;
    JPasswordField passwordField;
    GUI gui;

    public RegisterPane(GUI gui) {
        this.gui = gui;
        setName("RegisterPane");
        setLayout(new GridBagLayout());
        setBackground(GUI.MAIN_COLOR);
        addGuiParts();
    }

    @Override public void init() {
        usernameField.setText("");
        passwordField.setText("");
    }

    @Override public void cleanup() {}

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
        var titleLabel = GUIHelper.createDefaultLabel("The Wordle Game", 40);
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
        var registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 25));
        registerButton.setBackground(GUI.SECONDARY_COLOR);
        registerButton.setForeground(GUI.MAIN_COLOR);
        registerButton.addActionListener(
            (ActionEvent e) -> {
                Username username = new Username(usernameField.getText());
                Password password = new Password(new String(passwordField.getPassword()));
                boolean success = gui.getMainController().addUser(username, password);
                if (success) {
                    JOptionPane.showMessageDialog(this, "User registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register user.");
                }
        });
        buttonPanel.add(registerButton);
        centralPanel.add(buttonPanel);

        centralPanel.add(Box.createVerticalStrut(20));

        var LoginPanel = GUIHelper.createContainerPanel();
        var loginLabel = new JLabel("<HTML><U>Have an account already? Login</U></HTML>");
        loginLabel.setForeground(GUI.SECONDARY_COLOR);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.getMainController().getLoginController().wantToLogin();
            }
        });
        LoginPanel.add(loginLabel);
        centralPanel.add(LoginPanel);
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


