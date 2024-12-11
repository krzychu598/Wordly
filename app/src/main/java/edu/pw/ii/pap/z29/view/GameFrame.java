package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import edu.pw.ii.pap.z29.controller.GameController;

public class GameFrame extends JFrame{
    JLabel titleLabel;
    GUI gui;
    public GameFrame(GUI gui){
        super("Game");
        this.gui = gui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(GUI.BLACK);
        getContentPane().setLayout(new GridBagLayout());
        addGuiParts();
        pack();
        setVisible(true);
    }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(GUI.MAIN_COLOR);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        add(centralPanel);

        titleLabel = GUI.createTitleLabel(40);
        centralPanel.add(titleLabel);
    }
}
