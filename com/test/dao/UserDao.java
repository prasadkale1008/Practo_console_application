package dao;


import entity.User;
import java.sql.SQLException;

public interface UserDao {
    User login(String username, String password) throws SQLException;
    User getUserById(int userId) throws SQLException;
    void createUser(User user) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int userId) throws SQLException;
}
