package edu.pw.ii.pap.z29.model;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public interface Record<T> {
    public void serialize(T data, PreparedStatement stmt) throws SQLException;
    public T deserialize(ResultSet rset) throws SQLException;
}
