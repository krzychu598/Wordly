package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.*;

import edu.pw.ii.pap.z29.controller.ApiController;
import edu.pw.ii.pap.z29.controller.GameController;

public class GameFrame extends JFrame{
    Vector<Vector<JTextField>> allBoxes;
    JLabel titleLabel;
    GUI gui;
    int line;
    boolean isUpdating;
    int length;
    public GameFrame(GUI gui){
        super("Game");
        this.gui = gui;
        allBoxes = new Vector<Vector<JTextField>>();
        line = 0;
        isUpdating = true;
        length = gui.getMainController().getGameController().getWordLength();
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
 
        for (int j = 0; j < 6; ++j){
            var boxesPanel = new JPanel();
            boxesPanel.setBackground(GUI.MAIN_COLOR);
            var boxes = new Vector<JTextField>();
            for(int i = 0; i < length; ++i){
                var box = new JTextField();
                // box.setBackground(GUI.SECONDARY_COLOR);
                box.setColumns(2);
                var boxListener = new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        isUpdating = !isUpdating;
                        if(!isUpdating) {
                            update();
                        }
                    }
        
                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        //pass
                    }
        
                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        //pass
                    }
                    public void update(){
                        isUpdating = true;
                        SwingUtilities.invokeLater(() -> {
                            int a;
                            String text = box.getText();
                            if (text.length() > 1) {
                                box.setText(text.substring(text.length()-1));
                            }
                            char l = text.charAt(0);
                            if (!(l>='a' && l <= 'z') && !(l>='A' && l<='Z') ){
                                box.setText("");
                            }
                            box.setText(box.getText().toUpperCase());
                            if((a = allBoxes.get(line).indexOf(box) + 1) < allBoxes.get(line).size()){
                                allBoxes.get(line).get(a).requestFocus();
                            }
                        });
                        isUpdating = false;
                    }
                };
                box.getDocument().addDocumentListener(boxListener);
                boxes.addElement(box);
                boxesPanel.add(box);
            }
            allBoxes.addElement(boxes);
            centralPanel.add(boxesPanel);
        }
        var enter = new JButton("Enter");
        enter.setFont(new Font("Dialog", Font.BOLD, 25));
        enter.setBackground(GUI.SECONDARY_COLOR);
        enter.setForeground(GUI.MAIN_COLOR);
        enter.addActionListener((ActionEvent e)-> {
            String a = "";
            for (int i = 0; i < length; ++i){
                a = a.concat(allBoxes.get(line).get(i).getText());
            }
            var vals = new ArrayList<Integer>(gui.getMainController().getGameController().check(a));
            int i = 0;
            if (vals.size() == 0){
                System.out.println("word doesnt exist");
                System.out.println(a);
                return;
            }
            for (var val : vals){
                if(val == 0){
                    allBoxes.get(line).get(i).setBackground(GUI.GREEN);
                } else if (val == 1){
                    allBoxes.get(line).get(i).setBackground(GUI.YELLOW);
                } else{
                    allBoxes.get(line).get(i).setBackground(GUI.BLACK);
                }
                ++i;
            }
            ++line;
            allBoxes.get(line).get(0).requestFocus();

        });
        centralPanel.add(enter);
    }
}
