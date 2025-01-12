package edu.pw.ii.pap.z29.view;

import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUIHelper {
    public static JTextField formatTextField(JTextField field,
            Color text_color, Color background_color, Font font) {
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setForeground(text_color);
        field.setBackground(background_color);
        field.setFont(font);
        field.setBorder(BorderFactory.createLineBorder(text_color, 2, true));
        return field;
    }

    public static JLabel createDefaultLabel(String text, int font_size) {
        var label = new JLabel(text);
        label.setForeground(GUI.getSecondaryColor());
        label.setFont(new Font("Dialog", Font.PLAIN, font_size));
        return label;
    }

    public static JButton createDefaultButton(String text, int font_size) {
        var button = new JButton(text);
        button.setBackground(GUI.getSecondaryColor());
        button.setForeground(GUI.getMainColor());
        button.setFont(new Font("Dialog", Font.PLAIN, font_size));
        return button;
    }

    public static JPanel createContainerPanel() {
        return createContainerPanel(new FlowLayout());
    }
    
    public static JPanel createContainerPanel(LayoutManager layout) {
        var panel = new JPanel(layout);
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static JPanel createDefaultListPanel() {
        var listPanel = GUIHelper.createContainerPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.LINE_AXIS));
        var fieldPanel = GUIHelper.createContainerPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
        listPanel.add(fieldPanel);
        listPanel.add(Box.createHorizontalStrut(50));
        listPanel.add(Box.createHorizontalGlue());
        var valuePanel = GUIHelper.createContainerPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));
        listPanel.add(valuePanel);
        return listPanel;
    }

    public static JButton addBackButton(Container con, SpringLayout layout,
                                        ActionListener listener) {
        var backButton = GUIHelper.createDefaultButton("Back", 16);
        backButton.addActionListener(listener);
        con.add(backButton);
        layout.putConstraint(SpringLayout.WEST, backButton, 10, SpringLayout.WEST, con);
        layout.putConstraint(SpringLayout.NORTH, backButton, 10, SpringLayout.NORTH, con);
        return backButton;
    }
}
