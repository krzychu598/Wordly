package edu.pw.ii.pap.z29.view.utility;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.Box;


public class FormPanel extends JPanel {
    public JLabel label;
    public Component gap;
    public JTextField field;

    public FormPanel(JTextField field, String name, Color panel_color) {
        this.field = field;
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setOpaque(false);
        label = new JLabel(name);
        label.setForeground(field.getForeground());
        label.setFont(field.getFont());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        gap = Box.createVerticalStrut(10);
        add(gap);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(field);
    }
}
