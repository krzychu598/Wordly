package edu.pw.ii.pap.z29;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;


public class Database implements AutoCloseable {
    private Connection conn = null;
    private static final String DB_URL =
        "jdbc:oracle:thin:@//ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl";
    private static final String user = "wkukielk";
    private static final String password = "wkukielk";

    public Connection getConnection() throws SQLException {
        if (this.conn != null && !this.conn.isClosed())
            return this.conn;
        this.conn = connect();
        return this.conn;
    }

    private Connection connect() throws SQLException {
        Properties connProps = new Properties();
        connProps.put("user", Database.user);
        connProps.put("password", Database.password);
        return DriverManager.getConnection(Database.DB_URL, connProps);
    }

    public void close() throws SQLException {
        if (this.conn != null) {
            this.conn.close();
            this.conn = null;
        }
    }
}
