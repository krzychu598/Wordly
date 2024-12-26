package edu.pw.ii.pap.z29.view;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


class GUIHelper {
    public static JTextField formatTextField(JTextField field,
            Color text_color, Color background_color, Font font) {
        field.setForeground(text_color);
        field.setBackground(background_color);
        field.setFont(font);
        field.setBorder(BorderFactory.createLineBorder(text_color, 2, true));
        return field;
    }

    static JLabel createTitleLabel(String text, int font_size) {
        var titleLabel = new JLabel(text);
        titleLabel.setForeground(GUI.SECONDARY_COLOR);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, font_size));
        return titleLabel;
    }

    static JPanel createContainerPanel() {
        return createContainerPanel(new FlowLayout());
    }
    
    static JPanel createContainerPanel(LayoutManager layout) {
        var panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
}
