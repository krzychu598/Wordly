package edu.pw.ii.pap.z29;

import edu.pw.ii.pap.z29.controller.MainController;
import edu.pw.ii.pap.z29.controller.ApiController;

public class App {
    public static void main(String[] args) {
        /*
        System.out.println(ApiController.randomWord(40));
        String word = ApiController.randomWord(5);
        System.out.println(word);
        System.out.println(ApiController.definition(word));
        System.out.println(ApiController.definition("asdasd"));
        System.out.println(ApiController.check(word));
        System.out.println(ApiController.check("sadadadad"));
        */
        System.out.println("hello");
        MainController controller = new MainController();
        controller.run();
    }
}
