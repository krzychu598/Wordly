package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import edu.pw.ii.pap.z29.controller.MainController;


public class GUI {
    protected static final Color BLACK = Color.decode("#000000");
    protected static final Color MAIN_COLOR = Color.decode("#101820");
    protected static final Color SECONDARY_COLOR = Color.decode("#FEE715");
    protected static final Font PLAIN_FONT = new Font("Dialog", Font.PLAIN, 20);

    private MainController mainController;
    private LoginFrame loginFrame;
    private ProfileFrame profileFrame;
    private MainFrame mainFrame;
    private RegisterFrame registerFrame;

    public GUI(MainController mainController) {
        this.mainController = mainController;
    }

    static JLabel createTitleLabel(int font_size) {
        var titleLabel = new JLabel("The Wordle game");
        titleLabel.setForeground(GUI.SECONDARY_COLOR);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, font_size));
        return titleLabel;
    }

    private void createAndShowGUI() {
        loginFrame = new LoginFrame(this);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }


    public MainController getMainController() {
        return mainController;
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void disposeOfLoginFrame() {
        loginFrame.dispose();
        loginFrame = null;
    }
    public void disposeOfMainFrame() {
        mainFrame.dispose();
        mainFrame = null;
    }
    
    public void showRegisterFrame() {
        registerFrame = new RegisterFrame(this);
        registerFrame.setVisible(true);
    }

    public void showLoginFrame() {
        loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    public void showMainFrame() {
        mainFrame = new MainFrame(this);
    }

    public void showProfileFrame() {
        profileFrame = new ProfileFrame(mainController);
    }
}
