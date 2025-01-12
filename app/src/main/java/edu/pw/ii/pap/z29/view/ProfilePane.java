package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;

import javax.swing.*;
import edu.pw.ii.pap.z29.controller.ProfileController;
import edu.pw.ii.pap.z29.model.primitives.Password;
import edu.pw.ii.pap.z29.model.primitives.Score;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.ListPanel;
import edu.pw.ii.pap.z29.controller.ProfileController.UserData;
import edu.pw.ii.pap.z29.exception.UserDataException;
import java.util.Collections;


public class ProfilePane extends CardPane {
    private GUI gui;
    private SpringLayout layout;
    private UserData userData;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JCheckBox passwordToggle;

    public ProfilePane(GUI gui) {
        this.gui = gui;
        setName("ProfilePane");
        this.layout = new SpringLayout();
        setLayout(layout);
    }

    @Override
    public void init() throws PaneInitException {
        setBackground(GUI.getMainColor());
        addGuiParts();
        try {
            updateUserData();
        } catch (UserDataException e) {
            throw new PaneInitException(e);
        }
    }

    @Override
    public void cleanup() {
        removeAll();
    }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        centralPanel.setOpaque(false);
        add(centralPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, centralPanel,
        0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, centralPanel,
        0, SpringLayout.VERTICAL_CENTER, this);
        GUIHelper.addBackButton(this, layout, e -> {
            (new Thread(() ->
                getProfileController().goBack())).start();
        });

        var strut = (JComponent)Box.createHorizontalStrut(400);
        strut.setAlignmentX(LEFT_ALIGNMENT);
        centralPanel.add(strut);

        var listPanel = new ListPanel(50);
        listPanel.strut.setSize(new Dimension(50, 0));

        var scoreFieldLabel = GUIHelper.createDefaultLabel("Max score:", 20);
        listPanel.fieldPanel.add(scoreFieldLabel);
        this.scoreLabel = GUIHelper.createDefaultLabel("", 20);
        listPanel.valuePanel.add(scoreLabel);

        var levelFieldLabel = GUIHelper.createDefaultLabel("Level:", 20);
        listPanel.fieldPanel.add(levelFieldLabel);
        this.levelLabel = GUIHelper.createDefaultLabel("", 20);
        listPanel.valuePanel.add(levelLabel);
        
        var usernameFieldLabel = GUIHelper.createDefaultLabel("Username:", 20);
        listPanel.fieldPanel.add(usernameFieldLabel);
        this.usernameLabel = GUIHelper.createDefaultLabel("", 20);
        var usernameLabelParent = GUIHelper.createContainerPanel();
        usernameLabelParent.setLayout(new BoxLayout(usernameLabelParent, BoxLayout.LINE_AXIS));
        usernameLabelParent.add(usernameLabel);
        usernameLabel.addMouseListener(new UsernameEditListener(usernameLabel));
        listPanel.valuePanel.add(usernameLabelParent);

        this.passwordToggle = new JCheckBox("Show");
        passwordToggle.setForeground(GUI.getSecondaryColor());
        passwordToggle.setOpaque(false);
        passwordToggle.addActionListener(e -> {
            updatePasswordLabel();
        });
        var passwordFieldLabel = GUIHelper.createDefaultLabel("Password:", 20);
        listPanel.fieldPanel.add(passwordFieldLabel);
        this.passwordLabel = GUIHelper.createDefaultLabel("", 20);
        var passwordValueRow = GUIHelper.createContainerPanel();
        passwordValueRow.setLayout(new BoxLayout(passwordValueRow, BoxLayout.LINE_AXIS));
        passwordLabel.addMouseListener(new PasswordEditListener(passwordLabel));
        passwordValueRow.add(passwordLabel);
        passwordValueRow.add(Box.createHorizontalStrut(10));
        passwordValueRow.add(passwordToggle);
        listPanel.valuePanel.add(passwordValueRow);

        centralPanel.add(listPanel);
        centralPanel.add(Box.createVerticalStrut(30));
        var friendsButton = GUIHelper.createDefaultButton("Friends list", 16);
        friendsButton.addActionListener(e -> {
            (new Thread(() ->
            getProfileController().wantToSeeFriends())).start();
        });
        centralPanel.add(friendsButton);

        var historyButton = GUIHelper.createDefaultButton("Games history", 16);
        historyButton.setBackground(GUI.getSecondaryColor());
        historyButton.addActionListener(e -> {
            (new Thread(() ->
            getProfileController().wantToSeeGameHistory())).start();
        });
        centralPanel.add(historyButton);
        centralPanel.setMaximumSize(getPreferredSize());
        centralPanel.add(Box.createVerticalStrut(50));

        var deleteButton = GUIHelper.createDefaultButton("Delete account", 16);
        deleteButton.setBackground(new Color(200, 10, 10));
        deleteButton.addActionListener(e -> {
            (new Thread(() ->
            getProfileController().wantToDeleteAccount())).start();
        });
        centralPanel.add(deleteButton);
        centralPanel.setMaximumSize(getPreferredSize());
    }

    public void updateUserData() {
        this.userData = getProfileController().readUserData();
        var max_score = userData.getScores().size() != 0 ?
            Collections.max(userData.getScores(), (Score s1, Score s2) -> {
                if (s1.getScore() == s2.getScore())
                    return 0;
                return s1.getScore() < s2.getScore() ? -1 : 1;
            }).getScore() : 0;
        scoreLabel.setText("" + max_score);
        levelLabel.setText("" + userData.getLevel().getLevelNr());
        usernameLabel.setText(userData.getUser().getUsername().getUsername());
        updatePasswordLabel();
    }

    private void updatePasswordLabel() {
        if (passwordToggle.isSelected())
            passwordLabel.setText(userData.getPassword().getPassword());
        else
            passwordLabel.setText("*****");
    }

    private ProfileController getProfileController() {
        return gui.getMainController().getProfileController();
    }

    public void setDarkMode(boolean darkMode) {
        if (darkMode) {
            setBackground(GUI.getMainColor());
        } else {
            setBackground(java.awt.Color.WHITE);
        }
        revalidate();
        repaint();
    }


    private class UsernameEditListener extends MouseAdapter {
        JLabel usernameLabel;
        JTextField hiddenField;

        UsernameEditListener(JLabel usernameLabel) {
            this.usernameLabel = usernameLabel;
            this.hiddenField = GUIHelper.formatTextField(
                new JTextField(), GUI.getSecondaryColor(), GUI.getMainColor(), GUI.PLAIN_FONT);
            hiddenField.addActionListener(e -> {
                var newUsername = new Username(hiddenField.getText());
                var parent = hiddenField.getParent();
                parent.remove(hiddenField);
                parent.add(usernameLabel);
                parent.revalidate();
                parent.repaint();
                (new Thread(() ->
                    getProfileController().wantToUpdateUsername(newUsername))).start();
            });
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                hiddenField.setText(usernameLabel.getText());
                var parent = usernameLabel.getParent();
                parent.remove(usernameLabel);
                parent.add(hiddenField);
                parent.revalidate();
                parent.repaint();
                hiddenField.requestFocus();
            }
        }
    }

    private class PasswordEditListener extends MouseAdapter {
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));

        PasswordEditListener(JLabel passwordLabel) {
            newPasswordField.setFont(passwordLabel.getFont());
            confirmPasswordField.setFont(passwordLabel.getFont());
            panel.add(new JLabel("New Password:"));
            panel.add(newPasswordField);
            panel.add(new JLabel("Confirm Password:"));
            panel.add(confirmPasswordField);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                try {
                    int result = JOptionPane.showConfirmDialog(
                        ProfilePane.this,
                        panel,
                        "Change Password",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                    );
                    if (result == JOptionPane.OK_OPTION) {
                        String new_password = new String(newPasswordField.getPassword());
                        String confirm_password = new String(confirmPasswordField.getPassword());
                        changePassword(new_password, confirm_password);
                    }
                } finally {
                    newPasswordField.setText("");
                    confirmPasswordField.setText("");
                }
            }
        }

        private void changePassword(String new_password, String confirm_password) {
            if (new_password.equals(confirm_password) && !new_password.isEmpty()) {
                var password = new Password(new_password);
                if (!getProfileController().updatePassword(password))
                    GUIHelper.showError(ProfilePane.this, "Haven't updated the password.");
                else
                    updateUserData();
            } else
                GUIHelper.showError(ProfilePane.this, "Passwords do not match or are empty");
        }
    }
}
