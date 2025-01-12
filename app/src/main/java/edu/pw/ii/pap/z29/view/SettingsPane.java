package edu.pw.ii.pap.z29.view;

import java.awt.event.*;
import javax.swing.*;

import edu.pw.ii.pap.z29.controller.SettingsController;
import edu.pw.ii.pap.z29.view.utility.CardPane;


public class SettingsPane extends CardPane {
    GUI gui;
    SpringLayout layout;
    JCheckBox darkModeBox;
    JCheckBox privProfileBox;

    public SettingsPane(GUI gui) {
        this.gui = gui;
        setName("SettingsPanel");
        this.layout = new SpringLayout();
        setLayout(layout);
    }

    @Override public void init() {
        setBackground(GUI.getMainColor());
        addGuiParts();
    }

    @Override public void cleanup() { removeAll(); }

    private void addGuiParts() {
        var centralPanel = new JPanel();
        centralPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        centralPanel.setOpaque(false);
        add(centralPanel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, centralPanel,
        0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, centralPanel,
        0, SpringLayout.VERTICAL_CENTER, this);
        GUIHelper.addBackButton(this, layout, e -> {
            (new Thread(() ->
                getSettingsController().goBack())).start();
        });

        var strut = (JComponent)Box.createHorizontalStrut(400);
        strut.setAlignmentX(LEFT_ALIGNMENT);
        centralPanel.add(strut);

        var titlePanel = GUIHelper.createContainerPanel();
        var titleLabel = GUIHelper.createDefaultLabel("Settings", 40);
        titlePanel.add(titleLabel);
        centralPanel.add(titlePanel);
        centralPanel.add(Box.createVerticalStrut(30));

        var darkModeLabel = GUIHelper.createDefaultLabel("Dark mode", 20);
        this.darkModeBox = new JCheckBox();
        darkModeBox.setSelected(gui.isDarkMode());
        var darkModePanel = checkBoxPanel(darkModeBox, darkModeLabel, e -> {
            (new Thread(() -> gui.setDarkMode(darkModeBox.isSelected()))).start();
        });
        centralPanel.add(darkModePanel);

        var privProfileLabel = GUIHelper.createDefaultLabel("Private Profile", 20);
        this.privProfileBox = new JCheckBox();
        privProfileBox.setSelected(gui.isPrivateProfile());
        var privProfilePanel = checkBoxPanel(privProfileBox, privProfileLabel, e -> {
            (new Thread(() -> gui.setPrivateProfile(privProfileBox.isSelected()))).start();
        });
        centralPanel.add(privProfilePanel);
    }

    private SettingsController getSettingsController() {
        return gui.getMainController().getSettingsController();
    }

    private JPanel checkBoxPanel(JCheckBox cbox, JLabel label, ActionListener listener) {
        var panel = GUIHelper.createContainerPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS)); 
        cbox.setForeground(GUI.getSecondaryColor());
        cbox.setOpaque(false);
        cbox.addActionListener(listener);
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(cbox);
        return panel;
    }
}
