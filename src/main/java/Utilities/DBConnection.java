package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String databaseName = "U056SZ";
    private static final String database_URL = "jdbc:mysql://3.227.166.251/" + databaseName;
    private static final String username = "U056SZ";
    private static final String password= "53688429500";
    private static final String driver ="com.mysql.cj.jdbc.Driver";
    public static Connection connection;

    public static Connection getDatabaseConn(){
        return connection;
    }

    public static void startConnection()throws SQLException, ClassNotFoundException
    {
        Class.forName(driver);
        connection = DriverManager.getConnection(database_URL, username, password);
        System.out.println("Connected to Database Successfully");
    }

    public static void closeConnection()throws  SQLException
    {
        connection.close();
        System.out.println("Connection Closed");
    }
}
