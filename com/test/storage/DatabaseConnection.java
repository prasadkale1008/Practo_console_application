package storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/protocoanpc7699prasad"; // Change 'itv' to your actual database name
    private static final String USER = "root";  // MySQL username; change if necessary
    private static final String PASSWORD = "Achieve$1994"; // MySQL password; change if necessary
    
    /**
     * Establishes a connection to the MySQL database.
     *
     * @return A Connection object representing the database connection.
     * @throws SQLException If a database access error occurs or the URL is null.
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Create a connection to the database using the provided URL, user, and password
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure you have this line
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        }
        catch (SQLException e) {
            // Handle SQL exceptions and print error message
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw e;  // Rethrow the exception to allow higher layers to handle it
        }
        return connection;
    }
    
    /**
     * Closes the database connection.
     *
     * @param connection The Connection object to be closed. 
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                // Close the database connection
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                // Handle SQL exceptions that occur during connection closing
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}
