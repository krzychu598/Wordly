package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import edu.pw.ii.pap.z29.controller.MainController;

public class ProfileFrame extends JFrame {
    public static final Color MAIN_COLOR = Color.decode("#101820");
    public static final Color TEXT_COLOR = Color.decode("#FEE715");

    MainController mainController;
    int score = 5000;
    String username = "JHONATHAN";
    String password = "ziemniak12";

    public ProfileFrame(MainController mainController) {
        super("Profile");
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
    
        JLabel scoreLabel = new JLabel("Best score: " + score);
        scoreLabel.setForeground(TEXT_COLOR);
        scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(scoreLabel, gbc);
    
        JLabel usernameLabel = new JLabel("Username: " + username);
        usernameLabel.setForeground(TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(usernameLabel, gbc);
    
        JLabel passwordLabel = new JLabel("Password: " + "*****");
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);
    
        JCheckBox togglePasswordCheckbox = new JCheckBox("Show");
        togglePasswordCheckbox.setForeground(TEXT_COLOR);
        togglePasswordCheckbox.setBackground(MAIN_COLOR);
        togglePasswordCheckbox.addActionListener(e -> {
            boolean isPasswordVisible = togglePasswordCheckbox.isSelected();
            if (isPasswordVisible) {
                passwordLabel.setText("Password: " + password);
            } else {
                passwordLabel.setText("Password: *****");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(togglePasswordCheckbox, gbc);
    
        addEditFunctionality(usernameLabel, "Username: ", text -> username = text);
        addPasswordEditFunctionality(passwordLabel);
    
        JButton deleteButton = new JButton("Delete Account");
        deleteButton.setForeground(MAIN_COLOR);
        deleteButton.setBackground(TEXT_COLOR);
        deleteButton.setFont(new Font("Dialog", Font.BOLD, 16));
        deleteButton.setPreferredSize(deleteButton.getPreferredSize());
        deleteButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete your account?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
    
            if (response == JOptionPane.YES_OPTION) {
                //TODO delete user from database
                JOptionPane.showMessageDialog(
                    this,
                    "Account deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
                );
                //TODO return to login frame
            }
        });
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(deleteButton, gbc);
    }
    

    private void addPasswordEditFunctionality(JLabel passwordLabel) {
        passwordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JPasswordField newPasswordField = new JPasswordField();
                    JPasswordField confirmPasswordField = new JPasswordField();
    
                    newPasswordField.setFont(passwordLabel.getFont());
                    confirmPasswordField.setFont(passwordLabel.getFont());

                    newPasswordField.setPreferredSize(passwordLabel.getPreferredSize());
                    confirmPasswordField.setPreferredSize(passwordLabel.getPreferredSize());
    
                    JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
                    panel.add(new JLabel("New Password:"));
                    panel.add(newPasswordField);
                    panel.add(new JLabel("Confirm Password:"));
                    panel.add(confirmPasswordField);
    
                    int result = JOptionPane.showConfirmDialog(
                        passwordLabel.getParent(),
                        panel,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                    );

                    if (result == JOptionPane.OK_OPTION) {
                        String newPassword = new String(newPasswordField.getPassword());
                        String confirmPassword = new String(confirmPasswordField.getPassword());
    
                        if (newPassword.equals(confirmPassword) && !newPassword.isEmpty()) {
                            password = newPassword;
                            passwordLabel.setText("Password: *****");
                        } else {
                            JOptionPane.showMessageDialog(passwordLabel.getParent(),
                                    "Passwords do not match or are empty!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
    

    private void addEditFunctionality(JLabel label, String prefix, java.util.function.Consumer<String> updater) {
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String currentText = label.getText().substring(prefix.length());
    
                    JTextField textField = new JTextField(currentText);
                    textField.setFont(label.getFont());
    
                    textField.setForeground(Color.BLACK);
                    textField.setBackground(Color.WHITE);
                    textField.setCaretColor(Color.BLACK);
                    textField.setBorder(BorderFactory.createLineBorder(TEXT_COLOR));
    
                    textField.setPreferredSize(new Dimension(220, 24));
    
                    Container parent = label.getParent();
                    GridBagLayout layout = (GridBagLayout) parent.getLayout();
                    GridBagConstraints gbc = layout.getConstraints(label);
                    parent.remove(label);
                    parent.add(textField, gbc);
                    parent.revalidate();
                    parent.repaint();
    
                    textField.addActionListener(event -> {
                        String newText = textField.getText();
                        newText = newText.length() > 25 ? newText.substring(0, 25) : newText;
                        updater.accept(newText);
                        label.setText(prefix + newText);
                        parent.remove(textField);
                        parent.add(label, gbc);
                        parent.revalidate();
                        parent.repaint();
                    });
                    textField.requestFocus();
                }
            }
        });
    }
    
}
