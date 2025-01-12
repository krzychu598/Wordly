package edu.pw.ii.pap.z29.view;

import java.awt.*;
import javax.swing.*;
import edu.pw.ii.pap.z29.controller.ProfileController;
import edu.pw.ii.pap.z29.controller.MainController;
import edu.pw.ii.pap.z29.model.primitives.Score;
import edu.pw.ii.pap.z29.model.primitives.User;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.ListPanel;
import edu.pw.ii.pap.z29.controller.ProfileController.UserData;
import edu.pw.ii.pap.z29.exception.UserDataException;
import java.util.Collections;


public class FriendProfilePane extends CardPane {
    private GUI gui;
    private User user;
    private MainController mainController;
    private SpringLayout layout;
    private UserData userData;
    private JLabel scoreLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JCheckBox passwordToggle;

    public FriendProfilePane(GUI gui) {
        this.gui = gui;
        this.mainController = gui.getMainController();
        setName("FriendProfilePane");
        this.layout = new SpringLayout();
        setLayout(layout);
    }

    @Override
    public void init() throws PaneInitException {
        setBackground(GUI.getMainColor());
        addGuiParts();
        try {
            updateUserData(user);
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
        var backButton = GUIHelper.createDefaultButton("Back", 16);
        backButton.addActionListener(e -> {
            (new Thread(() ->
                getProfileController().goBack())).start();
            });
        add(backButton);
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, backButton, 10, SpringLayout.NORTH, this);

        var strut = (JComponent)Box.createHorizontalStrut(400);
        strut.setAlignmentX(LEFT_ALIGNMENT);
        centralPanel.add(strut);

        var listPanel = new ListPanel(50);
        listPanel.strut.setSize(new Dimension(50, 0));

        var scoreFieldLabel = GUIHelper.createDefaultLabel("Score:", 20);
        listPanel.fieldPanel.add(scoreFieldLabel);
        this.scoreLabel = GUIHelper.createDefaultLabel("", 20);
        listPanel.valuePanel.add(scoreLabel);

        var usernameFieldLabel = GUIHelper.createDefaultLabel("Username:", 20);
        listPanel.fieldPanel.add(usernameFieldLabel);
        this.usernameLabel = GUIHelper.createDefaultLabel("", 20);
        var usernameLabelParent = GUIHelper.createContainerPanel();
        usernameLabelParent.setLayout(new BoxLayout(usernameLabelParent, BoxLayout.LINE_AXIS));
        usernameLabelParent.add(usernameLabel);
        listPanel.valuePanel.add(usernameLabelParent);




        centralPanel.add(listPanel);
        centralPanel.add(Box.createVerticalStrut(30));

        centralPanel.add(Box.createVerticalStrut(40));


        var historyButton = GUIHelper.createDefaultButton("Add Friend", 16);
        historyButton.setBackground(GUI.getSecondaryColor());
        historyButton.addActionListener(e -> {
            (new Thread(() ->
                gui.getMainController().getFriendsController().addFriend(user.getUserId()))).start();
        });
        centralPanel.add(historyButton);
        centralPanel.setMaximumSize(getPreferredSize());

        centralPanel.setMaximumSize(getPreferredSize());
    }

    public void updateUserData(User user) {
        var scores = getProfileController().readScores(user.getUserId());
        var max_score = scores.size() != 0 ?
            Collections.max(scores, (Score s1, Score s2) -> {
                if (s1.getScore() == s2.getScore())
                    return 0;
                return s1.getScore() < s2.getScore() ? -1 : 1;
            }).getScore() : 0;
        scoreLabel.setText("" + max_score);
        usernameLabel.setText(user.getUsername().getUsername());
    }

    public void setUser(User user) {
        this.user = user;
    }

    private ProfileController getProfileController() {
        return gui.getMainController().getProfileController();
    }
}

