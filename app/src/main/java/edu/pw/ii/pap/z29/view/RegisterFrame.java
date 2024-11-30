package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;


public class RegisterFrame extends JFrame {
    public static final Color MAIN_COLOR = Color.decode("#578CB5");
    // public static final Color SECONDARY_COLOR = 300;
    public static final Color TEXT_COLOR = Color.decode("#000000");
    JLabel registerLabel;
    
    public RegisterFrame() {
        super("Register");
        setSize(520, 680);
        setLayout(null);
//        addGuiParts();
        setVisible(true);
    }
}

