package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import edu.pw.ii.pap.z29.controller.MainController;

import javax.swing.event.*;


public class RegisterFrame extends JFrame {
    public static final Color MAIN_COLOR = Color.decode("#101820");
    // public static final Color SECONDARY_COLOR = 300;
    public static final Color TEXT_COLOR = Color.decode("#FEE715");
    JLabel registerLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    MainController mainController;
    
    public RegisterFrame(MainController mainController) {
        super("Register");
        this.mainController = mainController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(MAIN_COLOR);
        addGuiParts();
        pack();
        setVisible(true);
    }

    private void addGuiParts() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        registerLabel = new JLabel("Register account");
        registerLabel.setForeground(TEXT_COLOR);
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(registerLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setBackground(MAIN_COLOR);
        usernameField.setForeground(TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setBackground(MAIN_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        // REGISTER BUTTON
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 25));
        registerButton.setBackground(TEXT_COLOR);
        registerButton.setForeground(MAIN_COLOR);
        registerButton.addActionListener(
            (ActionEvent e) -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean success = mainController.addUser(username, password);
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

    }
    

}


