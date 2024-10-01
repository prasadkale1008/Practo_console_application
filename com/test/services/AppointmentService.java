package services;

import java.sql.SQLException;
import java.util.List;

import daoImple.AppointmentDaoImpl;
import daoImple.ServiceDaoImpl; // Import the ServiceDaoImpl
import entity.Appointment;
import entity.Service;

public class AppointmentService {

    private AppointmentDaoImpl appointmentDAO = new AppointmentDaoImpl();
    private ServiceDaoImpl serviceDAO = new ServiceDaoImpl(); // Initialize ServiceDaoImpl

    public void scheduleAppointment(Appointment appointment) throws SQLException {
        appointmentDAO.createAppointment(appointment);
    }

    public Appointment getAppointmentById(int appointmentId) throws SQLException {
        return appointmentDAO.getAppointmentById(appointmentId);
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
        return appointmentDAO.getAppointmentsByPatientId(patientId);
    }

    public void updateAppointment(Appointment appointment) throws SQLException {
        appointmentDAO.updateAppointment(appointment);
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        appointmentDAO.deleteAppointment(appointmentId);
    }

    // New method to fetch all services
    public List<Service> getAllServices() throws SQLException {
        return serviceDAO.getAllServices();
    }
}
