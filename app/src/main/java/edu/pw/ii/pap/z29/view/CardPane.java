package edu.pw.ii.pap.z29.view;

import javax.swing.JPanel;
import lombok.experimental.StandardException;


public abstract class CardPane extends JPanel {
    abstract public void init() throws PaneInitException;
    abstract public void cleanup();

    @StandardException
    public class PaneInitException extends RuntimeException {}
}
