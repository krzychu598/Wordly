package edu.pw.ii.pap.z29.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.util.List;

import edu.pw.ii.pap.z29.controller.ProfileController.UserData;
import edu.pw.ii.pap.z29.view.utility.CardPane;
import edu.pw.ii.pap.z29.view.utility.MainPane;
import edu.pw.ii.pap.z29.model.primitives.Score;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;


public class GameHistoryPane extends CardPane {
    private GUI gui;
    private SpringLayout layout = new SpringLayout();
    private MainPane cardPanel;
    private UserData userData;

    public GameHistoryPane(GUI gui) {
        this.gui = gui;
        setName("GameHistoryPane");
        this.cardPanel = new MainPane();
        setLayout(layout);
    }

    @Override
    public void init() throws PaneInitException {
        setBackground(GUI.getMainColor());
        this.userData = gui.getMainController().getProfileController().readUserData();
        List<Score> scores = userData.getScores();
        addGuiParts(scores);
    }

    @Override
    public void cleanup() {
        cardPanel.clear();
        removeAll();
    }

    private void addGuiParts(List<Score> scores) {
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
                goBack())).start();
        });
        add(backButton);
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, backButton, 10, SpringLayout.NORTH, this);

        cardPanel.setAlignmentX(LEFT_ALIGNMENT);
        centralPanel.add(cardPanel);

        var scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
        scoresPanel.setOpaque(false);

        if (scores.isEmpty()) {
            var noScoresLabel = new JLabel("No game history available.");
            noScoresLabel.setForeground(GUI.getSecondaryColor());
            noScoresLabel.setAlignmentX(LEFT_ALIGNMENT);
            scoresPanel.add(noScoresLabel);
        } else {
            for (Score score : scores) {
                var scoreLabel = new JLabel("Score: " + score.getScore() + " | Word: " + score.getWord() + " | Date: " + score.getDate());
                scoreLabel.setForeground(GUI.getSecondaryColor());
                scoreLabel.setAlignmentX(LEFT_ALIGNMENT);
                scoresPanel.add(scoreLabel);
            }
        }

        centralPanel.add(scoresPanel);
    }

    private class ButtonListener implements ActionListener {
        CardPane paneToShow;

        public ButtonListener(CardPane paneToShow) {
            this.paneToShow = paneToShow;
        }

        public void actionPerformed(ActionEvent e) {
            ((Component)e.getSource()).setBackground(GUI.ORANGE);
            (new Thread(() -> cardPanel.showPane(paneToShow))).start();
        }
    }

    public void goBack() {
        gui.getMainController().getGui().showPane(GUI.Pane.Profile);
    }
}
