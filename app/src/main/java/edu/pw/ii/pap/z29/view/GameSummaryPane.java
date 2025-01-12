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
    JLabel image = null;
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
        var centralPanel = createCentralPanel();
        add(centralPanel);

        var titleLabel = GUIHelper.createDefaultLabel("Summary", 40);
        var scoreInfoPanel = createScoreInfoPanel();
        var playAgainButton = createPlayAgainButton();
        var exitToMainMenuButton = createExitToMainMenuButton();
        var buttonsPanel = new JPanel();
        buttonsPanel.setBackground(GUI.MAIN_COLOR);
        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(exitToMainMenuButton);

        centralPanel.add(titleLabel);
        centralPanel.add(scoreInfoPanel);
        centralPanel.add(buttonsPanel);
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
        String scoreMessage = String.format("Score: %d", score);
        infoLabel.setText(scoreMessage);
        if (score > gui.getMainController().getGameSummaryController().getCurrentBestScore()){
            try {
                if (image == null){
                    Image highScoreImage = ImageIO.read(new File("app/src/images/high-score.png"));
                    highScoreImage = highScoreImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                    image = new JLabel(new ImageIcon(highScoreImage));
                    scorePanel.add(image);
                }
                } catch (IOException e){
                    e.printStackTrace();
                }
        } else{
            if (image !=null){
                scorePanel.remove(image);
                image = null;
            }
        }

    }
    private JPanel createScoreInfoPanel(){
        scorePanel = new JPanel();
        scorePanel.setBackground(GUI.MAIN_COLOR);
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Dialog", Font.BOLD, 10));
        infoLabel.setBackground(GUI.SECONDARY_COLOR);
        infoLabel.setForeground(GUI.SECONDARY_COLOR);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setBorder(BorderFactory.createLineBorder(GUI.MAIN_COLOR, 2, true));
        infoLabel.setPreferredSize(new Dimension(200, 50));
        scorePanel.add(infoLabel);
        return scorePanel;
    }
}
