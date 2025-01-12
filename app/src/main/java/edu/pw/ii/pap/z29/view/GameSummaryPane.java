package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import edu.pw.ii.pap.z29.view.utility.CardPane;

public class GameSummaryPane extends CardPane{
    GUI gui;
    int score = 0;
    JPanel scorePanel;
    JLabel infoLabel;
    public GameSummaryPane(GUI gui){
        this.gui = gui;
        setName("GameSummaryPane");
        setBackground(GUI.MAIN_COLOR);
        setLayout(new GridBagLayout());
        addGuiParts();
    }
    @Override public void init() {
        updateInfo();
    }

    @Override public void cleanup() {}

    private JPanel createCentralPanel(){
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(GUI.MAIN_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        return centralPanel;
    }
    private void updateInfo(){
        score = gui.getMainController().getGameSummaryController().getScore();
        updatePanelInfo();
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
        var playAgainButton = new JButton("Play Again"); 
        playAgainButton.setFont(new Font("Dialog", Font.BOLD, 10));
        playAgainButton.setBackground(GUI.SECONDARY_COLOR);
        playAgainButton.setForeground(GUI.MAIN_COLOR);
        playAgainButton.setHorizontalAlignment(JButton.CENTER);
        playAgainButton.addActionListener((ActionEvent e)->{
            gui.getMainController().newGame(gui.getMainController().getGameController().getWordLength());
        });
        return playAgainButton;
    }
    private JButton createExitToMainMenuButton(){
        var exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Dialog", Font.BOLD, 10));
        exitButton.setBackground(GUI.SECONDARY_COLOR);
        exitButton.setForeground(GUI.MAIN_COLOR);
        exitButton.setHorizontalAlignment(JButton.CENTER);
        exitButton.addActionListener((ActionEvent e)->{
            gui.showPane(GUI.Pane.Home);

        });
        return exitButton;
    }
    
    private void updatePanelInfo(){
        //TODO fix imsge size, prettify
        String scoreMessage = String.format("Score: %d", score);
        infoLabel.setText(scoreMessage);
        if (score > gui.getMainController().getGameSummaryController().getCurrentHighScore()){
            try {
                BufferedImage highScore = ImageIO.read(new File("app/src/images/high-score.png"));
                JLabel image = new JLabel(new ImageIcon(highScore));
                scorePanel.add(image);
                } catch (IOException e){
                    e.printStackTrace();
                }
        }

    }
    private JPanel createScoreInfoPanel(){
        scorePanel = new JPanel();
        scorePanel.setBackground(GUI.BLACK);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Dialog", Font.BOLD, 10));
        infoLabel.setBackground(GUI.MAIN_COLOR);
        infoLabel.setForeground(GUI.SECONDARY_COLOR);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2, true));
        infoLabel.setPreferredSize(new Dimension(200, 50));
        scorePanel.add(infoLabel);
        return scorePanel;
    }
}
