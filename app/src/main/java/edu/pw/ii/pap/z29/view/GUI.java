package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.pw.ii.pap.z29.controller.MainController;


public class GUI {

    private MainController mainController;
    private LoginFrame loginFrame;
    private ProfileFrame profileFrame;
    private MainFrame mainFrame;
    private RegisterFrame registerFrame;

    public GUI(MainController mainController) {
        this.mainController = mainController;
    }

    private void createAndShowGUI() {
        loginFrame = new LoginFrame(this);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);


        //uncomment to test profileFrame
/*         profileFrame = new ProfileFrame(mainController);
        profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.pack();
        profileFrame.setVisible(true); */
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
        mainFrame.setVisible(false);
    }

    public void showProfileFrame() {
        profileFrame = new ProfileFrame(mainController);
    }
}
