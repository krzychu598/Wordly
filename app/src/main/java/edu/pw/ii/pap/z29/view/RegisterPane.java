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
        
    }

    @Override public void init() {
        setBackground(GUI.getMainColor());
        addGuiParts();
        usernameField.setText("");
        passwordField.setText("");
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
        var titleLabel = GUIHelper.createDefaultLabel("The Wordle Game", 40);
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
            new JPasswordField(), GUI.getSecondaryColor(), GUI.getMainColor(), GUI.PLAIN_FONT);
        var passwordPanel = new FormPanel(passwordField, "Password", GUI.getMainColor());
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(passwordPanel);

        centralPanel.add(Box.createVerticalStrut(40));

        var buttonPanel = GUIHelper.createContainerPanel(new GridLayout());
        var registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 25));
        registerButton.setBackground(GUI.getSecondaryColor());
        registerButton.setForeground(GUI.getMainColor());
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
        loginLabel.setForeground(GUI.getSecondaryColor());
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.getMainController().getLoginController().wantToLogin();
            }
        });
        LoginPanel.add(loginLabel);
        centralPanel.add(LoginPanel);
    }
}


