package dao;
import entity.Appointment;
import java.sql.SQLException;
import java.util.List;

public interface AppointmentDao {
    void createAppointment(Appointment appointment) throws SQLException;
    Appointment getAppointmentById(int appointmentId) throws SQLException;
    List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException;
    void updateAppointment(Appointment appointment) throws SQLException;
    void deleteAppointment(int appointmentId) throws SQLException;
}
