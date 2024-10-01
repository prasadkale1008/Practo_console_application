package dao;

import entity.Patient;
import java.sql.SQLException;
import java.util.List;

public interface PatientDao {
    void createPatient(Patient patient) throws SQLException;
    Patient getPatientById(int patientId) throws SQLException;
    List<Patient> getAllPatients() throws SQLException;  // This should be declared here
    void updatePatient(Patient patient) throws SQLException;
    void deletePatient(int patientId) throws SQLException;
}
