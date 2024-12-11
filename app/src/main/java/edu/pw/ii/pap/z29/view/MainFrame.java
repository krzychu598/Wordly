package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;

import edu.pw.ii.pap.z29.controller.MainController;



public class MainFrame extends JFrame {
    JButton playButton;

    GUI gui;

    public MainFrame(GUI gui) {
        super("Main");
        this.gui = gui;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(GUI.MAIN_COLOR);
        getContentPane().setLayout(new GridBagLayout());
        addGuiParts();
        pack();
        setVisible(true);
    }

    private void addGuiParts() {
        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        var profileMenu = createProfileMenu();
        menuBar.add(profileMenu);
        var centralPanel = createCentralPanel();
        this.add(centralPanel);

        var titleLabel = GUI.createTitleLabel(60);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        centralPanel.add(titleLabel);

        centralPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        var buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setMaximumSize(new Dimension(200, 100));
        buttonPanel.setBorder(BorderFactory.createLineBorder(GUI.BLACK, 5, true));
        buttonPanel.setOpaque(false);
        playButton = createPlayButton();
        buttonPanel.add(playButton);
        centralPanel.add(buttonPanel);
    }

    private JPanel createCentralPanel() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setBackground(GUI.MAIN_COLOR);
        centralPanel.setOpaque(false);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        return centralPanel;
    }

    private JMenu createProfileMenu() {
        var menu = new JMenu("Profile");
        var logoutAction = new LogoutAction();
        logoutAction.putValue(Action.NAME, "Log Out");
        menu.add(logoutAction);
        var seeProfileAction = new SeeProfileAction();
        seeProfileAction.putValue(Action.NAME, "See Profile");
        menu.add(seeProfileAction);
        return menu;
    }

    JButton createPlayButton() {
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Dialog", Font.BOLD, 40));
        playButton.setBackground(GUI.SECONDARY_COLOR);
        playButton.setForeground(GUI.MAIN_COLOR);
        playButton.addActionListener(
            (ActionEvent e) -> {
                JPopupMenu pop = new JPopupMenu("Settings");
                JSlider lengthSlider = new JSlider(4, 10, 7);
                lengthSlider.setMajorTickSpacing(1);
                lengthSlider.setPaintTicks(true);
                lengthSlider.setPaintLabels(true);
                JCheckBox definitionBox = new JCheckBox("Show Definition");
                JButton start = new JButton("Start");
                start.addActionListener((ActionEvent f)->{
                    MainFrame.this.dispose();
                    gui.getMainController().newGame(lengthSlider.getValue(), definitionBox.isSelected());;
                    
                });
                pop.add(new JLabel("Word length:"));
                pop.add(lengthSlider);
                pop.add(definitionBox);
                pop.add(start);
                pop.show(playButton, playButton.getWidth() / 2, playButton.getHeight());
            });
        return playButton;
    }

    private class LogoutAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            MainFrame.this.dispose();
            gui.getMainController().getLoginController().wantToLogout();
        }
    }

    private class SeeProfileAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            MainFrame.this.dispose();
            gui.getMainController().getLoginController().seeProfile();
        }
    }
}
