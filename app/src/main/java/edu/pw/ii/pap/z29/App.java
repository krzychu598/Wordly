package edu.pw.ii.pap.z29;

import java.sql.SQLException;


public class App {
    public static void main(String[] args) {
        System.out.println("hello");
        try (var database = new Database()){
            var conn = database.getConnection();
            System.out.println(conn.getSchema());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
