package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.util.Vector;

import javax.swing.*;

import edu.pw.ii.pap.z29.view.utility.CardPane;

public class GameSummaryPane extends CardPane{
    GUI gui;
    public GameSummaryPane(GUI gui){
        this.gui = gui;
        setName("GameSummaryPane");
        setBackground(GUI.MAIN_COLOR);
        setLayout(new GridBagLayout());
        addGuiParts();
    }
    @Override public void init() {}

    @Override public void cleanup() {}

    private JPanel createCentralPanel(){
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(GUI.MAIN_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        return centralPanel;
    }

    private void addGuiParts(){
        //TODO project GameSummaryPane
        //You win/you lose, score, new best, play again, exit, new user score
        var centralPanel = createCentralPanel();
        add(centralPanel);

        var titleLabel = GUIHelper.createDefaultLabel("Summary", 40);
        var scoreInfoPanel = createScoreInfoPanel();
        var playAgainButton = createPlayAgainButton();
        var exitToMainMenuButton = createExitToMainMenuButton();

        centralPanel.add(titleLabel);
        centralPanel.add(scoreInfoPanel);
        centralPanel.add(playAgainButton);
        centralPanel.add(exitToMainMenuButton);
    }

    private JButton createPlayAgainButton(){

    }
    private JButton createExitToMainMenuButton(){

    }
    private JLabel createScoreInfoPanel(){

    }
}
