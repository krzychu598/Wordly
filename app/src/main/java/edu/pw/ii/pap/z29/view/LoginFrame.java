package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;



public class LoginFrame extends JFrame {
    public static final Color MAIN_COLOR = Color.decode("#578CB5");
    public static final Color TEXT_COLOR = Color.decode("#000000");
    JLabel loginLabel;

    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        addGuiParts();
        pack(); // Adjust the frame size to fit all the content
        setVisible(true);
    }

    private void addGuiParts() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding

        loginLabel = new JLabel("login");
        loginLabel.setForeground(TEXT_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(loginLabel, gbc);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
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


        JTextField passwordField = new JPasswordField(20);
        passwordField.setBackground(MAIN_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);


        JLabel registerLabel = new JLabel("Don't have account? Register");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setForeground(TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame.this.dispose();

                new RegisterFrame().setVisible(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc. gridwidth = 2;
        add(registerLabel,gbc);


    }
}
