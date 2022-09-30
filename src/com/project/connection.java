package com.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    public static Connection connectDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cts", "dhruv", "1234");
            return con;
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }

}
