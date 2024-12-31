package edu.pw.ii.pap.z29.view.utility;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Component;

import edu.pw.ii.pap.z29.view.GUIHelper;

public class ListPanelVertical extends JPanel {
    public ListPanelVertical() {
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    public void addRow(Row row) {
        add(row);
    }

    public static class Row extends JPanel {
        public Row() {
            setOpaque(false);
            setAlignmentX(Component.LEFT_ALIGNMENT);
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        }

        public void set(Component field, Component value) {
            removeAll();
            add(field);
            add(Box.createHorizontalGlue());
            add(value);
        }
    }
}
