package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import edu.pw.ii.pap.z29.view.utility.CardPane;



public class HomePane extends CardPane {
    JButton playButton;
    JPopupMenu pop;
    GUI gui;

    public HomePane(GUI gui) {
        this.gui = gui;
        setName("HomePane");
        setBackground(GUI.MAIN_COLOR);
        setLayout(new GridBagLayout());
        addGuiParts();
    }

    @Override
    public void init() {
        var menuBar = new JMenuBar();
        var profileMenu = createProfileMenu();
        menuBar.add(profileMenu);
        gui.getFrame().setJMenuBar(menuBar);
    }

    @Override
    public void cleanup() {
        gui.getFrame().setJMenuBar(null);
    }

    private void addGuiParts() {
        var centralPanel = createCentralPanel();
        this.add(centralPanel);

        var titleLabel = GUIHelper.createDefaultLabel("The Wordle Game", 60);
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

    public void setDarkMode(boolean darkMode) {
        if (darkMode) {
            setBackground(GUI.MAIN_COLOR);
        } else {
            setBackground(java.awt.Color.WHITE);
        }
        revalidate();
        repaint();
    }


    JButton createPlayButton() {
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Dialog", Font.BOLD, 40));
        playButton.setBackground(GUI.SECONDARY_COLOR);
        playButton.setForeground(GUI.MAIN_COLOR);
        playButton.addActionListener(
            (ActionEvent e) -> {
                pop = new JPopupMenu("Settings");
                JSlider lengthSlider = new JSlider(4, 10, 7);
                lengthSlider.setMajorTickSpacing(1);
                lengthSlider.setPaintTicks(true);
                lengthSlider.setPaintLabels(true);
                JButton start = new JButton("Start");
                start.addActionListener((ActionEvent f)->{
                    gui.getMainController().newGame(lengthSlider.getValue());
                    pop.setVisible(false);
                });
                pop.add(new JLabel("Word length:"));
                pop.add(lengthSlider);
                pop.add(start);
                pop.show(playButton, playButton.getWidth() / 2, playButton.getHeight());
            });
        return playButton;
    }

    private class LogoutAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gui.getMainController().getLoginController().wantToLogout();
        }
    }

    private class SeeProfileAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            gui.getMainController().getLoginController().seeProfile();
        }
    }
}
