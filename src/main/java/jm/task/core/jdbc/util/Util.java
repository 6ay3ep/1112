package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "6ay3ep";
    private static final String PASSWORD = "Ra0365314_";


    public static Connection getConnection() {
        Connection connection = null;
        try  {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подкючения");
        }
        return connection;
    }
}
