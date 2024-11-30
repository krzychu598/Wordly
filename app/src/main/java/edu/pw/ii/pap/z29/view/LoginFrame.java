package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;



public class LoginFrame extends JFrame {
    public static final Color MAIN_COLOR = Color.decode("#578CB5");
    // public static final Color SECONDARY_COLOR = 300;
    public static final Color TEXT_COLOR = Color.decode("#000000");
    JLabel loginLabel;

    public LoginFrame() {
        super("Login");
        // setSize(520, 680);
        // setLayout(null);
        addGuiParts();
        // setVisible(true);
    }

    private void addGuiParts() {
        loginLabel = new JLabel("login");
        loginLabel.setBounds(0 , 25, 520, 100);
        loginLabel.setForeground(TEXT_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);


        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(30, 150, 400, 25);
        usernameLabel.setForeground(TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        
        
        JTextField usernameField = new JTextField();
        usernameField.setBounds(30, 185, 380, 55);
        usernameField.setBackground(MAIN_COLOR);
        usernameField.setForeground(TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(30, 315, 400, 25);
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        JTextField passwordField = new JTextField();
        passwordField.setBounds(30, 350, 380, 55);
        passwordField.setBackground(MAIN_COLOR);
        passwordField.setForeground(TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(passwordLabel);
        add(passwordField);


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
        registerLabel.setBounds(125, 600, 250, 30);
        add(registerLabel);


    }
}
