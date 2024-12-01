package edu.pw.ii.pap.z29.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Dimension;


class FormPanel extends JPanel {
    JLabel label;
    Component gap;
    JTextField field;

    FormPanel(JTextField field, String name, Color panel_color) {
        this.field = field;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(panel_color);
        label = new JLabel(name);
        label.setForeground(field.getForeground());
        label.setFont(field.getFont());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        gap = Box.createRigidArea(new Dimension(0, 10));
        add(gap);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(field);
    }
}
