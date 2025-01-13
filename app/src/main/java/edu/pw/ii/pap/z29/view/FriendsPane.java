package edu.pw.ii.pap.z29.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.util.List;
import edu.pw.ii.pap.z29.exception.UserDataException;
import edu.pw.ii.pap.z29.model.primitives.Friendship;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.model.primitives.Username;
import edu.pw.ii.pap.z29.view.friends.*;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.MainPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;


public class FriendsPane extends CardPane {
    private GUI gui;
    private SpringLayout layout = new SpringLayout();
    private List<Friendship> friendships;
    private MainPane cardPanel;
    private ActualFriendsPane actualPane;
    private ReceivedInvitationsPane receivedPane;
    private SentInvitationsPane sentPane;

    private JButton actualButton;
    private JButton receivedButton;
    private JButton sentButton;

    public FriendsPane(GUI gui) {
        this.gui = gui;
        setName("FriendsPane");
        this.cardPanel = new MainPane();
        this.actualPane = new ActualFriendsPane(gui);
        this.receivedPane = new ReceivedInvitationsPane(gui);
        this.sentPane = new SentInvitationsPane(gui);
        cardPanel.addPane(actualPane);
        cardPanel.addPane(receivedPane);
        cardPanel.addPane(sentPane);
        setLayout(layout);
    }

    @Override
    public void init() throws PaneInitException {
        setBackground(GUI.getMainColor());
        try {
            friendships = gui.getMainController().getProfileController().readFriendships();
        } catch (UserDataException e) {
            throw new PaneInitException(e);
        }
        actualPane.setFriends(extractActualFriends(friendships));
        receivedPane.setReceived(extractReceived(friendships));
        sentPane.setSent(extractSent(friendships));
        addGuiParts();
        cardPanel.showPane(actualPane);
    }

    @Override
    public void cleanup() {
        cardPanel.clear();
        removeAll();
    }

    private List<Integer> extractActualFriends(List<Friendship> friendships) {
        int user_id = gui.getMainController().getLoginController().getCurrentUser().getUserId();
        var actualFriendships = friendships.stream()
            .filter((f) -> { return !f.isPending(); })
            .map((f) -> { return f.getInvited() == user_id ? f.getInviting() : f.getInvited();});
        return actualFriendships.toList();
    }

    private List<Integer> extractReceived(List<Friendship> friendships) {
        int user_id = gui.getMainController().getLoginController().getCurrentUser().getUserId();
        var received = friendships.stream()
            .filter((f) -> { return f.isPending() && f.getInvited() == user_id; })
            .map((f) -> { return f.getInviting(); });
        return received.toList();
    }

    private List<Integer> extractSent(List<Friendship> friendships) {
        int user_id = gui.getMainController().getLoginController().getCurrentUser().getUserId();
        var received = friendships.stream()
            .filter((f) -> { return f.isPending() && f.getInviting() == user_id; })
            .map((f) -> { return f.getInvited(); });
        return received.toList();
    }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setOpaque(false);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);
        layout.putConstraint(SpringLayout.NORTH, centralPanel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, centralPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);

        var backButton = GUIHelper.createDefaultButton("Back", 16);
        backButton.addActionListener(e -> {
            (new Thread(() ->
                gui.getMainController().getFriendsController().goBack())).start();
            });
        add(backButton);
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, backButton, 10, SpringLayout.NORTH, this);

        JPanel findFriendsPanel = createFindFriendsPanel();
        centralPanel.add(findFriendsPanel);
        var navigationPanel = GUIHelper.createContainerPanel();
        navigationPanel.setAlignmentX(LEFT_ALIGNMENT);

        actualButton = GUIHelper.createDefaultButton("Friends", 16);
        actualButton.addActionListener(new ButtonListener(actualPane));
        if (gui.isDarkMode()) {
            actualButton.setBackground(GUI.ORANGE);
        } else {
            actualButton.setBackground(GUI.getSecondaryColor());
        }
        navigationPanel.add(actualButton);

        receivedButton = GUIHelper.createDefaultButton("Received", 16);
        receivedButton.addActionListener(new ButtonListener(receivedPane));
        navigationPanel.add(receivedButton);

        sentButton = GUIHelper.createDefaultButton("Sent", 16);
        sentButton.addActionListener(new ButtonListener(sentPane));
        navigationPanel.add(sentButton);

        centralPanel.add(navigationPanel);
        cardPanel.setAlignmentX(LEFT_ALIGNMENT);
        centralPanel.add(cardPanel);
    }

    private JPanel createFindFriendsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        inputPanel.setOpaque(false);
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        var usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(200, 30));
        usernameField.setText("Type username here...");
        usernameField.setForeground(GUI.BLACK);
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Type username here...")) {
                    usernameField.setText("");
                    usernameField.setForeground(GUI.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(GUI.BLACK);
                    usernameField.setText("Type username here...");
                }
            }
        });
        inputPanel.add(usernameField);

        inputPanel.add(Box.createRigidArea(new Dimension(10, 0)));


        var findFriendsButton = createFindFriendsButton(usernameField);
        inputPanel.add(findFriendsButton);

        panel.add(inputPanel);

        return panel;
    }


    private JButton createFindFriendsButton(JTextField usernameField) {
        JButton button = GUIHelper.createDefaultButton("Find Friend", 16);
        Dimension fixedSize = new Dimension(120, 30);
        button.setPreferredSize(fixedSize);
        button.setMinimumSize(fixedSize);
        button.setMaximumSize(fixedSize);
        button.addActionListener((e) -> {
                String input = usernameField.getText().trim();
                if (input.isEmpty() || input.equals("Type username here...")) {
                    JOptionPane.showMessageDialog(this, "Please enter a username.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Username username = new Username(input);
                try {
                    User user = gui.getMainController().getFriendsController().readUser(username);
                    var pane = (FriendProfilePane)gui.getPane(GUI.Pane.FriendProfilePane);
                    pane.setUser(user);
                    if (gui.getMainController().getSettingsController().isPrivate(user.getUserId())) {
                        JOptionPane.showMessageDialog(this, "User has a private profile.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        gui.showPane(GUI.Pane.FriendProfilePane);
                    }

                }
                catch (UserDataException e1) {
                    JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        });
        return button;
    }

    private class ButtonListener implements ActionListener {
        CardPane paneToShow;

        public ButtonListener(CardPane paneToShow) {
            this.paneToShow = paneToShow;
        }

        public void actionPerformed(ActionEvent e) {
                actualButton.setBackground(GUI.getSecondaryColor());
                receivedButton.setBackground(GUI.getSecondaryColor());
                sentButton.setBackground(GUI.getSecondaryColor());
                ((Component)e.getSource()).setBackground(GUI.ORANGE);
            (new Thread(() -> cardPanel.showPane(paneToShow))).start();
        }
    }
}
