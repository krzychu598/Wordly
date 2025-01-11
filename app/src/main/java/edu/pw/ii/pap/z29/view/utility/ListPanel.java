package edu.pw.ii.pap.z29.view.utility;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Component;

import edu.pw.ii.pap.z29.view.GUIHelper;

public class ListPanel extends JPanel {
    public JPanel fieldPanel;
    public Component strut;
    public Component glue;
    public JPanel valuePanel;;

    public ListPanel(int strut_length) {
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.fieldPanel = GUIHelper.createContainerPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.PAGE_AXIS));
        add(fieldPanel);
        this.strut = Box.createHorizontalStrut(strut_length);
        add(strut);
        this.glue = Box.createHorizontalGlue();
        add(glue);
        this.valuePanel = GUIHelper.createContainerPanel();
        valuePanel.setLayout(new BoxLayout(valuePanel, BoxLayout.PAGE_AXIS));
        add(valuePanel);
    }    
}
