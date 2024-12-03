package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import edu.pw.ii.pap.z29.controller.MainController;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.primitives.Username;

import javax.swing.event.*;


public class RegisterFrame extends JFrame {
    JLabel registerLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    GUI gui;
    
    public RegisterFrame(GUI gui) {
        super("Register");
        this.gui = gui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(GUI.MAIN_COLOR);
        addGuiParts();
        pack();
        setVisible(true);
    }

    private void addGuiParts() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        registerLabel = new JLabel("Register account");
        registerLabel.setForeground(GUI.SECONDARY_COLOR);
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registerLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(GUI.SECONDARY_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setBackground(GUI.MAIN_COLOR);
        usernameField.setForeground(GUI.SECONDARY_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(GUI.SECONDARY_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setBackground(GUI.MAIN_COLOR);
        passwordField.setForeground(GUI.SECONDARY_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        // REGISTER BUTTON
        JButton registerButton = new JButton("REGISTER");
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

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        JLabel loginLabel = new JLabel("Have an account? Login");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setForeground(GUI.SECONDARY_COLOR);
    
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterFrame.this.dispose();
                gui.getMainController().getLoginController().wantToLogin();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginLabel, gbc);

    }
    

}


