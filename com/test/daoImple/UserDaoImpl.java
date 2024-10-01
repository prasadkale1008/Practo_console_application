package daoImple;

import dao.UserDao;
import entity.User;
import storage.DatabaseConnection;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }
    @Override
    public User login(String username, String password) throws SQLException {
    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, username);  // Set username as the first parameter
        stmt.setString(2, password);  // Set password as the second parameter
        
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));  
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password")); 
            user.setRole(rs.getString("role"));
            return user;
        }
    } catch (SQLException e) {
        e.printStackTrace();  
        throw e;  
    }
    return null;  // Return null if no user is found
}


@Override
public User getUserById(int userId) throws SQLException {
    String query = "SELECT * FROM users WHERE user_id = ?";  // Use the correct column name
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));  // Update to the correct column name
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));  // Handle securely
            return user;
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Print stack trace for exception
        throw e;  // Rethrow if necessary
    }
    return null;  // Return null if no user is found
}

 @Override
public void createUser(User user) throws SQLException {
    String query = "INSERT INTO users (username, password, role,) VALUES (?, ?, 'user',)";


    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setDate(3, user.getDateOfBirth());  // This now works after adding getDateOfBirth() in User class
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}



    
    

    @Override
    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ? WHERE user_id = ?";  // Use the correct column name

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // Consider hashing the password before updating
            stmt.setInt(3, user.getUserId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE user_id = ?";  // Use the correct column name

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
