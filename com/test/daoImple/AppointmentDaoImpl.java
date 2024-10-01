package daoImple;

import dao.AppointmentDao;
import dao.UserDao;
import entity.Appointment;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import storage.DatabaseConnection;

public class AppointmentDaoImpl implements AppointmentDao {

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public void createAppointment(Appointment appointment) throws SQLException {
        String query = "INSERT INTO appointments (patient_id, user_id, appointment_date, appointment_time, test_type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointment.getPatient().getPatientId());
            stmt.setInt(2, appointment.getUser().getUserId());
            stmt.setDate(3, appointment.getAppointmentDate());
            stmt.setTime(4, appointment.getAppointmentTime());
            stmt.setString(5, appointment.getTestType());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace for any SQL exceptions
            throw e;  // Rethrow if necessary
        }
    }
@Override
public Appointment getAppointmentById(int appointmentId) throws SQLException {
    String query = "SELECT * FROM appointments WHERE appointment_id = ?";  // Update to correct column name
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, appointmentId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(rs.getInt("appointment_id"));  // Update to correct column name
            
            UserDao userDao = new UserDaoImpl();

            // Retrieve the User object based on user_id
            int userId = rs.getInt("user_id");
            User user = userDao.getUserById(userId); // Assuming you have a method to get User by ID
            appointment.setUser(user);  // Set the user object in the appointment
            
            appointment.setAppointmentDate(rs.getDate("appointment_date"));
            appointment.setAppointmentTime(rs.getTime("appointment_time"));
            appointment.setTestType(rs.getString("test_type"));
            return appointment;
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Print stack trace for debugging
        throw e;  // Rethrow exception after logging
    }
    return null;  // Return null if no appointment is found
}
@Override
public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
    String query = "SELECT * FROM appointments WHERE patient_id = ?";
    List<Appointment> appointments = new ArrayList<>();
    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, patientId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(rs.getInt("appointment_id"));  // Update to correct column name

            UserDao userDao = new UserDaoImpl();
            
            // Retrieve the User object based on user_id
            int userId = rs.getInt("user_id");
            User user = userDao.getUserById(userId); // Assuming you have a method to get User by ID
            appointment.setUser(user);  // Set the user object in the appointment
            
            appointment.setAppointmentDate(rs.getDate("appointment_date"));
            appointment.setAppointmentTime(rs.getTime("appointment_time"));
            appointment.setTestType(rs.getString("test_type"));
            appointments.add(appointment);
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Print stack trace for debugging
        throw e;  // Rethrow exception after logging
    }
    return appointments;
    }
    @Override
    public void updateAppointment(Appointment appointment) throws SQLException {
        String query = "UPDATE appointments SET appointment_date = ?, appointment_time = ?, test_type = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, appointment.getAppointmentDate());
            stmt.setTime(2, appointment.getAppointmentTime());
            stmt.setString(3, appointment.getTestType());
            stmt.setInt(4, appointment.getAppointmentId());  // Assuming appointmentId is the primary key
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace for any SQL exceptions
            throw e;  // Rethrow if necessary
        }
    }

    @Override
    public void deleteAppointment(int appointmentId) throws SQLException {
        String query = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Print the stack trace for any SQL exceptions
            throw e;  // Rethrow if necessary
        }
    }
}
