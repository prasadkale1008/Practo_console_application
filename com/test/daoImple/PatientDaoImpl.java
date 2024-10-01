package daoImple;

import dao.PatientDao;
import entity.Patient;
import storage.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public void createPatient(Patient patient) throws SQLException {
        String query = "INSERT INTO patients (first_name, last_name, dob, contact_number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, patient.getDateOfBirth());
            stmt.setString(4, patient.getContactNumber());
            stmt.setString(5, patient.getAddress());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for SQLException
            throw e;  // Rethrow exception if needed
        }
    }
    @Override
    public Patient getPatientById(int patientId) throws SQLException {
        String query = "SELECT * FROM patients WHERE patient_id = ?";  // Use the correct column name
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));  // Update to the correct column name
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDateOfBirth(rs.getDate("dob"));
                patient.setContactNumber(rs.getString("contact_number"));
                patient.setAddress(rs.getString("address"));
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for SQLException
            throw e;  // Rethrow exception if needed
        }
        return null;  // Return null if no patient is found
    }
    
    

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        String query = "SELECT * FROM patients";  // SQL query to fetch all patients
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("id"));  // Ensure this matches your DB schema
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDateOfBirth(rs.getDate("dob"));
                patient.setContactNumber(rs.getString("contact_number"));
                patient.setAddress(rs.getString("address"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for SQLException
            throw e;  // Rethrow exception if needed
        }
        return patients;  // Return the list of patients
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        String query = "UPDATE patients SET first_name = ?, last_name = ?, dob = ?, contact_number = ?, address = ? WHERE patient_id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, patient.getDateOfBirth());
            stmt.setString(4, patient.getContactNumber());
            stmt.setString(5, patient.getAddress());
            stmt.setInt(6, patient.getPatientId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for SQLException
            throw e;  // Rethrow exception if needed
        }
    }

    @Override
    public void deletePatient(int patientId) throws SQLException {
        String query = "DELETE FROM patients WHERE patient_id = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Print stack trace for SQLException
            throw e;  // Rethrow exception if needed
        }
    }
}
