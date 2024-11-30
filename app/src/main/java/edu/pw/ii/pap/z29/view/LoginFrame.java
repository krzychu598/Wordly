package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import edu.pw.ii.pap.z29.controller.MainController;



public class LoginFrame extends JFrame {
    public static final Color MAIN_COLOR = Color.decode("#101820");
    public static final Color TEXT_COLOR = Color.decode("#FEE715");
    JLabel loginLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    MainController mainController;

    public LoginFrame(MainController mainController) {
        super("Login");
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

        loginLabel = new JLabel("The Wordle game");
        loginLabel.setForeground(TEXT_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 40));
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

        // LOGIN BUTTON
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loginButton.setBackground(TEXT_COLOR);
        loginButton.setForeground(MAIN_COLOR);
        loginButton.addActionListener(
            (ActionEvent e) -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                mainController.getLoginController().checkLogin(username, password);
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

    //REGISTER BUTTON

        JLabel registerLabel = new JLabel("Don't have account? Register");
        registerLabel.setForeground(TEXT_COLOR);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame.this.dispose();

                new RegisterFrame().setVisible(true);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(registerLabel, gbc);
    }
    

}
