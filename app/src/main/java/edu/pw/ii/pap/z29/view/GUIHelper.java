package edu.pw.ii.pap.z29.view;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;


public class GUIHelper {
    public static JTextField formatTextField(JTextField field,
            Color text_color, Color background_color, Font font) {
        field.setForeground(text_color);
        field.setBackground(background_color);
        field.setFont(font);
        field.setBorder(BorderFactory.createLineBorder(text_color, 2, true));
        return field;
    }
}
