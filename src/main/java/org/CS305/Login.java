package org.CS305;

import java.sql.*;

public class Login {

    public Integer enter(String id, String pass) throws ClassNotFoundException, SQLException {
        String TestURL = "jdbc:postgresql://localhost:5433/";
        String TestUserName = "postgres";
        String TestPassword = "dbms";
        Class.forName("org.postgresql.Driver");
        Connection c = DriverManager.getConnection(TestURL, TestUserName, TestPassword);
        Statement s = c.createStatement();

        String query = "Select count(*) from users where userid = '"+id+"' and password = '"+pass+"';";
        ResultSet rs = s.executeQuery(query);
        rs.next();
        if(rs.getInt(1)==1){
            String y = "Select enrollyear from users where userid = '"+id+"';";
            rs = s.executeQuery(y);
            rs.next();
            return rs.getInt(1);
        }
        else {
            return 0;
        }
    }
}
