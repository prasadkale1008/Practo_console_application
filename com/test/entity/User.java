package entity;
import java.sql.Date;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role; 
    private Date dateOfBirth;  // Use java.sql.Date for the date of birth

    // Getter for dateOfBirth
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Setter for dateOfBirth
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Other fields, getters, and setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role; 
    }

    public void setRole( String role ) {
        this.role = role;
    }
}
