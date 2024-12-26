package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import edu.pw.ii.pap.z29.controller.MainController;
import lombok.Data;
import edu.pw.ii.pap.z29.model.ScoresTable;
import edu.pw.ii.pap.z29.model.UsersTable;
import edu.pw.ii.pap.z29.model.primitives.User;
import java.util.Optional;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.model.primitives.LoginPassword;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.LoginPasswordTable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class ProfilePane extends CardPane {
    public static final Color MAIN_COLOR = Color.decode("#101820");
    public static final Color TEXT_COLOR = Color.decode("#FEE715");

    private GUI gui;
    private MainController mainController;
    private ScoresTable scores;
    private UsersTable users;
    private LoginPasswordTable passwords;
    private int user_id;

    private LoginPassword loginPassword;
    private Password password;
    private List<Integer> userScores;
    private User user;
    private Username username;
    private int score;


    public ProfilePane(GUI gui) {
        this.gui = gui;
        this.mainController = gui.getMainController();
        setName("ProfilePane");
        this.scores = mainController.getScores();
        this.users = mainController.getUsers();
        this.passwords = mainController.getLoginPasswords();
        setLayout(new GridBagLayout());
        setBackground(MAIN_COLOR);
    }

    @Override
    public void init() {
        this.user_id = mainController.getLoginController().getCurrentUser().getUserId();
        try {
            this.loginPassword = passwords.read(user_id).orElseThrow(() -> 
            new IllegalArgumentException("No login password found for user ID: " + user_id)
            );
            this.password = loginPassword.getPassword();
            this.userScores = scores.readAllScores(user_id);
            this.user = users.read(user_id).orElseThrow(() -> 
            new IllegalArgumentException("No user found for user ID: " + user_id)
            );
            this.username = user.getUsername();
            if (userScores.size() != 0){
                this.score = userScores.get(0);
            } else{
                this.score = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error reading user data from the database. Please try again later.");
            this.userScores = new ArrayList<>();
            this.password = new Password("NOPASS");
            this.loginPassword = new LoginPassword(user_id, password);
            this.username = new Username("NONAME");
            this.user = new User(username);
            this.score = 0;
        }
        addGuiParts();
    }

    @Override
    public void cleanup() {
        removeAll();;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    //Collections.sort(List<Integer> userScores, Collections.reverseOrder()); //sort in reverse
    


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
    
        JLabel usernameLabel = new JLabel("Username: " + username.getUsername());
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
                passwordLabel.setText("Password: " + password.getPassword());
            } else {
                passwordLabel.setText("Password: *****");
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(togglePasswordCheckbox, gbc);
    
        addUsernameEdit(usernameLabel, "Username: ");
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
                try{users.delete(user_id);
                scores.delete(user_id);
                passwords.delete(user_id);}
                catch (SQLException  s){
                    s.printStackTrace();
                    showError("Error reading user data from the database. Please try again later.");
                }
                JOptionPane.showMessageDialog(
                    this,
                    "Account deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
                );
                //TODO go back to login frame
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
                            password.setPassword(newPassword);
                            loginPassword.setPassword(password);

                            try{passwords.update(loginPassword);}
                            catch (SQLException  s){
                                s.printStackTrace();
                                showError("Error reading user data from the database. Please try again later.");
                            }
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
    

    private void addUsernameEdit(JLabel label, String prefix) {
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
                        username.setUsername(newText);
                        user.setUsername(username);
                        try{users.update(user_id, user);}
                        catch (SQLException  s){
                            s.printStackTrace();
                            showError("Error reading user data from the database. Please try again later.");
                        }
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
