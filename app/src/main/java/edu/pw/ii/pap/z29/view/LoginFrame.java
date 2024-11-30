package edu.pw.ii.pap.z29.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;


public class LoginFrame extends JFrame {
    JLabel helloLabel;

    public LoginFrame() {
        super("LoginFrame");
        helloLabel = new JLabel("Hello");
        this.getContentPane().add(helloLabel, BorderLayout.CENTER);
    }
}
