package edu.pw.ii.pap.z29.view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.pw.ii.pap.z29.controller.MainController;


public class GUI {

    private MainController mainController;
    private LoginFrame loginFrame;
    private ProfileFrame profileFrame;
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
    
    public void showRegisterFrame() {
        registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
    }
}
