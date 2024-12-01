package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import edu.pw.ii.pap.z29.controller.MainController;



public class LoginFrame extends JFrame {
    public static final Color BLACK = Color.decode("#000000");
    public static final Color MAIN_COLOR = Color.decode("#101820");
    public static final Color TEXT_COLOR = Color.decode("#FEE715");
    public static final Color MY_PANEL_COLOR = Color.decode("#406080");
    public static final Font PLAIN_FONT = new Font("Dialog", Font.PLAIN, 20);
    JLabel titleLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    MainController mainController;

    public LoginFrame(MainController mainController) {
        super("Login");
        this.mainController = mainController;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BLACK);
        getContentPane().setLayout(new GridBagLayout());
        addGuiParts();
        pack();
        setVisible(true);
    }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(MAIN_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);

        titleLabel = new JLabel("The Wordle game");
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        centralPanel.add(titleLabel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        usernameField = GUIHelper.formatTextField(
            new JTextField(), TEXT_COLOR, MAIN_COLOR, PLAIN_FONT);
        var usernamePanel = new FormPanel(usernameField, "Username", MAIN_COLOR);
        usernamePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(usernamePanel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        passwordField = (JPasswordField)GUIHelper.formatTextField(
            new JPasswordField(), TEXT_COLOR, MAIN_COLOR, PLAIN_FONT);
        var passwordPanel = new FormPanel(passwordField, "Password", MAIN_COLOR);
        passwordPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        centralPanel.add(passwordPanel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(titleLabel.getPreferredSize());
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loginButton.setBackground(TEXT_COLOR);
        loginButton.setForeground(MAIN_COLOR);
        loginButton.addActionListener(
            (ActionEvent e) -> {
                String username = usernamePanel.field.getText();
                String password = new String(passwordField.getPassword());
                mainController.getLoginController().checkLogin(username, password);
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
        registerLabel.setForeground(TEXT_COLOR);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFrame.this.dispose();
                
                new RegisterFrame().setVisible(true);
            }
        });
        registerLabel.setAlignmentX(CENTER_ALIGNMENT);
        innerRegisterPanel.add(registerLabel);
        registerPanel.add(innerRegisterPanel);
        centralPanel.add(registerPanel);
    }
}
