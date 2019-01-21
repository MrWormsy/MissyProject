package fr.mrwormsy.missyproject;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class MissySQL {
	
	//TODO SAY THAT THE FIRST TIME THEY WILL GET AN ERROR BECAUSE THE SQL's SETTINGS ARE NOT SET
	
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    //private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/OmniFriends";
    //private static final String USERNAME = "root";
    //private static final String PASSWORD = "";
    private static final String MAX_POOL = "250";

    private static Connection connection;
    private static Properties properties;

    // create properties
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "");
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // connect database
    static public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                //connection = (Connection) DriverManager.getConnection(DATABASE_URL, getProperties());
                connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/MissyProject", getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    static public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static Connection getConnection() {
		return connection;
	}
	
}
