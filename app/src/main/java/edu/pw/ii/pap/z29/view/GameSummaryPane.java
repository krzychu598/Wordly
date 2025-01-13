package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import edu.pw.ii.pap.z29.view.utility.CardPane;

public class GameSummaryPane extends CardPane{
    GUI gui;
    int score = 0;
    JPanel imagesPanel;
    JPanel scorePanel;
    JLabel infoLabel;
    JLabel image = null;
    JLabel image2 = null;
    public GameSummaryPane(GUI gui){
        this.gui = gui;
        setName("GameSummaryPane");
        setLayout(new GridBagLayout());
    }

    @Override public void init() {
        setBackground(GUI.getMainColor());
        addGuiParts();
        updateInfo();
    }

    @Override public void cleanup() {
        removeAll();
    }

    private JPanel createCentralPanel(){
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(GUI.getMainColor());
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
        buttonsPanel.setBackground(GUI.getMainColor());
        buttonsPanel.add(playAgainButton);
        buttonsPanel.add(exitToMainMenuButton);

        centralPanel.add(titleLabel);
        centralPanel.add(scoreInfoPanel);
        centralPanel.add(buttonsPanel);
    }

    private JButton createPlayAgainButton(){
        var playAgainButton = new JButton("Play Again"); 
        playAgainButton.setFont(new Font("Dialog", Font.BOLD, 10));
        playAgainButton.setBackground(GUI.getSecondaryColor());
        playAgainButton.setForeground(GUI.getMainColor());
        playAgainButton.setHorizontalAlignment(JButton.CENTER);
        playAgainButton.addActionListener((ActionEvent e)->{
            gui.getMainController().newGame(gui.getMainController().getGameController().getWordLength());
        });
        return playAgainButton;
    }
    private JButton createExitToMainMenuButton(){
        var exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Dialog", Font.BOLD, 10));
        exitButton.setBackground(GUI.getSecondaryColor());
        exitButton.setForeground(GUI.getMainColor());
        exitButton.setHorizontalAlignment(JButton.CENTER);
        exitButton.addActionListener((ActionEvent e)->{
            gui.showPane(GUI.Pane.Home);

        });
        return exitButton;
    }
    
    private void updatePanelInfo(){
        int newLevel = gui.getMainController().getGameSummaryController().getNewLevel();
        int level = gui.getMainController().getGameSummaryController().getLevel();
        boolean isHighScore = gui.getMainController().getGameSummaryController().getCurrentBestScore() < score;
        String scoreMessage = String.format("Score: %d  Your Level: %d", score, level);

        if (newLevel > level){
            scoreMessage += String.format("---> %d", newLevel);
        }
        infoLabel.setText(scoreMessage);

        if (isHighScore){
            try {
                Image highScoreImage = ImageIO.read(new File("src/images/high-score.png"));
                if (image == null){
                    highScoreImage = highScoreImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                    image = new JLabel(new ImageIcon(highScoreImage));
                    imagesPanel.add(image);
                }
                } catch (IOException e){
                    try{
                    Image highScoreImage = ImageIO.read(new File("app/src/images/high-score.png"));
                    if (image == null){
                        highScoreImage = highScoreImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                        image = new JLabel(new ImageIcon(highScoreImage));
                        imagesPanel.add(image);
                    }
                } catch (Exception y){
                    e.printStackTrace();
                    y.printStackTrace();
                }
                }
        } else{
            if (image !=null){
                imagesPanel.remove(image);
                image = null;
            }
        }
        if (newLevel > level){

            try {
                Image newLevelImage = ImageIO.read(new File("src/images/level-up.png"));
                if (image2 == null){
                    newLevelImage = newLevelImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                    image2 = new JLabel(new ImageIcon(newLevelImage));
                    imagesPanel.add(image2);
                }
                } catch (IOException e){
                    try{
                    Image newLevelImage = ImageIO.read(new File("app/src/images/level-up.png"));
                    if (image2 == null){
                        newLevelImage = newLevelImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                        image2 = new JLabel(new ImageIcon(newLevelImage));
                        imagesPanel.add(image2);
                    }
                } catch (Exception y){
                    e.printStackTrace();
                    y.printStackTrace();
                }
                }
        }else{
            if (image2 !=null){
                imagesPanel.remove(image2);
                image2 = null;
            }
        }

    }
    private JPanel createScoreInfoPanel(){
        scorePanel = new JPanel();
        scorePanel.setBackground(GUI.getMainColor());
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Dialog", Font.BOLD, 10));
        infoLabel.setBackground(GUI.getSecondaryColor());
        infoLabel.setForeground(GUI.getSecondaryColor());
        // infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setBorder(BorderFactory.createLineBorder(GUI.getMainColor(), 2, true));
        infoLabel.setPreferredSize(new Dimension(200, 50));
        scorePanel.add(infoLabel);
        imagesPanel = new JPanel();
        imagesPanel.setBackground(GUI.getMainColor());
        imagesPanel.setLayout(new FlowLayout());
        scorePanel.add(imagesPanel);
        return scorePanel;
    }
}
