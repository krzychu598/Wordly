package edu.pw.ii.pap.z29;

import edu.pw.ii.pap.z29.controller.MainController;
import edu.pw.ii.pap.z29.controller.ApiController;

public class App {
    public static void main(String[] args) {
        System.out.println("hello");
        MainController controller = new MainController();
        controller.run();
    }
}
