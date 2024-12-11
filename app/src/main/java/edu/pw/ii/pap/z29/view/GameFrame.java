package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import edu.pw.ii.pap.z29.controller.GameController;

public class GameFrame extends JFrame{
    Vector<JTextField> boxes;
    JLabel titleLabel;
    GUI gui;
    public GameFrame(GUI gui){
        super("Game");
        this.gui = gui;
        boxes = new Vector<JTextField>();
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
        for(int i = 0; i < gui.getMainController().getGamController().getWordLength(); ++i){
            boxes.addElement(new JTextField());
            centralPanel.add(boxes.lastElement());
        }
    }
}
