package edu.pw.ii.pap.z29.controller;

import java.sql.SQLException;

import javax.xml.crypto.Data;

import edu.pw.ii.pap.z29.Database;
import edu.pw.ii.pap.z29.view.GUI;
import edu.pw.ii.pap.z29.model.SQLLogger;
import edu.pw.ii.pap.z29.model.UsersTable;
import edu.pw.ii.pap.z29.model.LoginPasswordTable;


public class MainController {
    GUI gui = new GUI();
    SQLLogger sqlLogger = new SQLLogger();
    Database database = new Database();
    UsersTable users;
    LoginPasswordTable loginPasswords;

    public void run() {
        try {
            var conn = database.getConnection();
            users = new UsersTable(conn);
            loginPasswords = new LoginPasswordTable(conn);
        } catch (SQLException e) {
            sqlLogger.log(e);
        }
        gui.run();
    }
}
