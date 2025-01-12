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
        setLayout(new GridBagLayout());
    }

    @Override public void init() {
        setBackground(GUI.getMainColor());
        addGuiParts();
    }

    @Override public void cleanup() {
        removeAll();
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
            new JTextField(), GUI.getSecondaryColor(), GUI.getMainColor(), GUI.PLAIN_FONT);
        var usernamePanel = new FormPanel(usernameField, "Username", GUI.getMainColor());
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(usernamePanel);

        centralPanel.add(Box.createVerticalStrut(20));

        passwordField = (JPasswordField)GUIHelper.formatTextField(
            new JPasswordField(),  GUI.getSecondaryColor(), GUI.getMainColor(), GUI.PLAIN_FONT);
        var passwordPanel = new FormPanel(passwordField, "Password", GUI.getMainColor());
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(passwordPanel);

        centralPanel.add(Box.createVerticalStrut(40));

        var buttonPanel = GUIHelper.createContainerPanel(new GridLayout());
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loginButton.setBackground(GUI.getSecondaryColor());
        loginButton.setForeground(GUI.getMainColor());
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
        registerLabel.setForeground(GUI.getSecondaryColor());
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.getMainController().getLoginController().wantToRegister();
            }
        });
        registerPanel.add(registerLabel);
        centralPanel.add(registerPanel);
    }
}
