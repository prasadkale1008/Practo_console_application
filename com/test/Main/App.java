package Main;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

import entity.User;
import entity.Patient;
import entity.Appointment;
import entity.Service; // Import Service entity
import services.AppointmentService;
import daoImple.UserDaoImpl;
import daoImple.PatientDaoImpl;
import daoImple.ServiceDaoImpl; // Import ServiceDaoImpl

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            UserDaoImpl userDAO = new UserDaoImpl();
            PatientDaoImpl patientDAO = new PatientDaoImpl();
            AppointmentService appointmentService = new AppointmentService();
            ServiceDaoImpl serviceDAO = new ServiceDaoImpl(); // Initialize ServiceDaoImpl

            // User Login
            System.out.println("Enter your username:");
            String username = scanner.nextLine();
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            User loggedInUser = userDAO.login(username, password);
            if (loggedInUser == null) {
                System.out.println("Invalid username or password. Exiting...");
                return;
            }

            System.out.println("Login successful!");

            boolean continueApp = true;
            while (continueApp) {
                if( loggedInUser.getRole().trim().equals("Admin") ) {
                    System.out.println("Select an option:");
                    System.out.println("1. Create Patient");
                    System.out.println("2. View Patient");
                    System.out.println("3. Schedule Appointment");
                    System.out.println("4. View Appointment");
    
                    int choice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline after integer input
    
                    switch (choice) {
                        case 1:
                            // Case 1: Create a new Patient
                            try {
                                System.out.println("Enter first name:");
                                String firstName = scanner.nextLine();
    
                                System.out.println("Enter last name:");
                                String lastName = scanner.nextLine();
    
                                System.out.println("Enter date of birth (yyyy-mm-dd):");
                                String dobInput = scanner.nextLine();
                                Date dob = Date.valueOf(dobInput);
    
                                System.out.println("Enter contact number:");
                                String contactNumber = scanner.nextLine();
    
                                System.out.println("Enter address:");
                                String address = scanner.nextLine();
    
                                // Create and save a new patient
                                Patient newPatient = new Patient();
                                newPatient.setFirstName(firstName);
                                newPatient.setLastName(lastName);
                                newPatient.setDateOfBirth(dob);
                                newPatient.setContactNumber(contactNumber);
                                newPatient.setAddress(address);
    
                                patientDAO.createPatient(newPatient);
                                System.out.println("Patient created successfully!");
                            } catch (SQLException e) {
                                System.out.println("Error creating patient: " + e.getMessage());
                            }
                            break;
    
                        case 2:
                            // Case 2: View a Patient
                            try {
                                System.out.println("Enter Patient ID:");
                                int patientId = scanner.nextInt();  
                                scanner.nextLine(); 
    
                                Patient patient = patientDAO.getPatientById(patientId);
                                if (patient == null) {
                                    System.out.println("Patient not found.");
                                } else {
                                    System.out.println("Patient Details:");
                                    System.out.println("ID: " + patient.getPatientId());
                                    System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                                    System.out.println("Date of Birth: " + patient.getDateOfBirth());
                                    System.out.println("Contact Number: " + patient.getContactNumber());
                                    System.out.println("Address: " + patient.getAddress());
                                }
                            } catch (SQLException e) {
                                System.out.println("Error fetching patient: " + e.getMessage());
                            }
                            break;
    
                        case 3:
                            // Case 3: Schedule a new appointment
                            try {
                                System.out.println("Enter User ID:");
                                int userId = scanner.nextInt();
                                scanner.nextLine();
    
                                User user = userDAO.getUserById(userId);
                                if (user == null) {
                                    System.out.println("User not found.");
                                    return;
                                }
    
                                System.out.println("Enter Patient ID:");
                                int patientId1 = scanner.nextInt();  
                                scanner.nextLine();
    
                                Patient patientForAppointment = patientDAO.getPatientById(patientId1);
                                if (patientForAppointment == null) {
                                    System.out.println("Patient not found.");
                                    return;
                                }
    
                                System.out.println("Enter appointment date (yyyy-mm-dd):");
                                String appointmentDateInput = scanner.nextLine();
                                Date appointmentDate = Date.valueOf(appointmentDateInput);
    
                                System.out.println("Enter appointment time (HH:mm:ss):");
                                String appointmentTimeInput = scanner.nextLine();
                                Time appointmentTime = Time.valueOf(appointmentTimeInput);
    
                                // Fetch and display available services
                                System.out.println("Available services:");
                                List<Service> services = appointmentService.getAllServices();
                                for (Service service : services) {
                                    System.out.println("Service ID: " + service.getServiceId());
                                    System.out.println("Name: " + service.getName());
                                    System.out.println("Description: " + service.getDescription());
                                    System.out.println("Price: " + service.getPrice());
                                    System.out.println("--------------------------");
                                }
    
                                System.out.println("Enter Service ID for the appointment:");
                                int serviceId = scanner.nextInt();
                                scanner.nextLine();
    
                                Service selectedService = services.stream()
                                    .filter(s -> s.getServiceId() == serviceId)
                                    .findFirst()
                                    .orElse(null);
    
                                if (selectedService == null) {
                                    System.out.println("Invalid Service ID.");
                                    return;
                                }
    
                                Appointment appointment = new Appointment();
                                appointment.setPatient(patientForAppointment);
                                appointment.setUser(user);
                                appointment.setAppointmentDate(appointmentDate);
                                appointment.setAppointmentTime(appointmentTime);
                                appointment.setTestType(selectedService.getName()); // Set service name as test type
    
                                appointmentService.scheduleAppointment(appointment);
                                System.out.println("Appointment scheduled successfully!");
                            } catch (SQLException e) {
                                System.out.println("Error scheduling appointment: " + e.getMessage());
                            }
                            break;
    
                        case 4:
                            // Case 4: View Appointments for a Patient
                            try {
                                System.out.println("Enter Patient ID:");
                                int patientIdForAppointment = scanner.nextInt();
                                scanner.nextLine();  
    
                                List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientIdForAppointment);
                                if (appointments.isEmpty()) {
                                    System.out.println("No appointments found for this patient.");
                                } else {
                                    System.out.println("Appointments for Patient ID " + patientIdForAppointment + ":");
                                    for (Appointment app : appointments) {
                                        System.out.println("Date: " + app.getAppointmentDate());
                                        System.out.println("Time: " + app.getAppointmentTime());
                                        System.out.println("Test Type: " + app.getTestType());
                                        System.out.println("Assigned User ID: " + app.getUser().getUserId());
                                        System.out.println("--------------------------");
                                    }
                                }
                            } catch (SQLException e) {
                                System.out.println("Error fetching appointments: " + e.getMessage());
                            }
                            break;
    
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }

                }
                else {
                    System.out.println("Options for the user" + loggedInUser.getRole() );
 
                     // User options
                     System.out.println("Options for User:");
                     System.out.println("1. Create Patient");
                     System.out.println("2. View Patient");
                     System.out.println("3. Schedule Appointment");
                     System.out.println("4. View Appointment");
                     // Add user-specific options as needed

                     System.out.println("Please enter your choice: ");
                     int choice = scanner.nextInt();
                     scanner.nextLine();

                     switch (choice) {
                        case 1:
                            // Create a new Patient
                            try {
                                System.out.println("Enter first name:");
                                String firstName = scanner.nextLine();
                                System.out.println("Enter last name:");
                                String lastName = scanner.nextLine();
                                System.out.println("Enter date of birth (yyyy-mm-dd):");
                                String dobInput = scanner.nextLine();
                                Date dob = Date.valueOf(dobInput);
                                System.out.println("Enter contact number:");
                                String contactNumber = scanner.nextLine();
                                System.out.println("Enter address:");
                                String address = scanner.nextLine();
    
                                Patient newPatient = new Patient();
                                newPatient.setFirstName(firstName);
                                newPatient.setLastName(lastName);
                                newPatient.setDateOfBirth(dob);
                                newPatient.setContactNumber(contactNumber);
                                newPatient.setAddress(address);
    
                                patientDAO.createPatient(newPatient);
                                System.out.println("Patient created successfully!");
                            } catch (SQLException e) {
                                System.out.println("Error creating patient: " + e.getMessage());
                            }
                            break;
    
                        case 2:
                            // View a Patient
                            try {
                                System.out.println("Enter Patient ID:");
                                int patientId = scanner.nextInt();  
                                scanner.nextLine(); 
                                Patient patient = patientDAO.getPatientById(patientId);
                                if (patient == null) {
                                    System.out.println("Patient not found.");
                                } else {
                                    System.out.println("Patient Details:");
                                    System.out.println("ID: " + patient.getPatientId());
                                    System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                                    System.out.println("Date of Birth: " + patient.getDateOfBirth());
                                    System.out.println("Contact Number: " + patient.getContactNumber());
                                    System.out.println("Address: " + patient.getAddress());
                                }
                            } catch (SQLException e) {
                                System.out.println("Error fetching patient: " + e.getMessage());
                            }
                            break;
    
                        case 3:
                            // Schedule a new appointment
                            try {
                                System.out.println("Enter User ID:");
                                int userId = scanner.nextInt();
                                scanner.nextLine();
                                User user = userDAO.getUserById(userId);
                                if (user == null) {
                                    System.out.println("User not found.");
                                    return;
                                }
    
                                System.out.println("Enter Patient ID:");
                                int patientId1 = scanner.nextInt();  
                                scanner.nextLine();
                                Patient patientForAppointment = patientDAO.getPatientById(patientId1);
                                if (patientForAppointment == null) {
                                    System.out.println("Patient not found.");
                                    return;
                                }
    
                                System.out.println("Enter appointment date (yyyy-mm-dd):");
                                String appointmentDateInput = scanner.nextLine();
                                Date appointmentDate = Date.valueOf(appointmentDateInput);
    
                                System.out.println("Enter appointment time (HH:mm:ss):");
                                String appointmentTimeInput = scanner.nextLine();
                                Time appointmentTime = Time.valueOf(appointmentTimeInput);
    
                                // Fetch and display available services
                                System.out.println("Available services:");
                                List<Service> services = appointmentService.getAllServices();
                                for (Service service : services) {
                                    System.out.println("Service ID: " + service.getServiceId());
                                    System.out.println("Name: " + service.getName());
                                    System.out.println("Description: " + service.getDescription());
                                    System.out.println("Price: " + service.getPrice());
                                    System.out.println("--------------------------");
                                }
    
                                System.out.println("Enter Service ID for the appointment:");
                                int serviceId = scanner.nextInt();
                                scanner.nextLine();
    
                                Service selectedService = services.stream()
                                    .filter(s -> s.getServiceId() == serviceId)
                                    .findFirst()
                                    .orElse(null);
    
                                if (selectedService == null) {
                                    System.out.println("Invalid Service ID.");
                                    return;
                                }
    
                                Appointment appointment = new Appointment();
                                appointment.setPatient(patientForAppointment);
                                appointment.setUser(user);
                                appointment.setAppointmentDate(appointmentDate);
                                appointment.setAppointmentTime(appointmentTime);
                                appointment.setTestType(selectedService.getName()); // Set service name as test type
    
                                appointmentService.scheduleAppointment(appointment);
                                System.out.println("Appointment scheduled successfully!");
                            } catch (SQLException e) {
                                System.out.println("Error scheduling appointment: " + e.getMessage());
                            }
                            break;
    
                        case 4:
                            // View Appointments for a Patient
                            try {
                                System.out.println("Enter Patient ID:");
                                int patientIdForAppointment = scanner.nextInt();
                                scanner.nextLine();  
    
                                List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientIdForAppointment);
                                if (appointments.isEmpty()) {
                                    System.out.println("No appointments found for this patient.");
                                } else {
                                    System.out.println("Appointments for Patient ID " + patientIdForAppointment + ":");
                                    for (Appointment app : appointments) {
                                        System.out.println("Date: " + app.getAppointmentDate());
                                        System.out.println("Time: " + app.getAppointmentTime());
                                        System.out.println("Test Type: " + app.getTestType());
                                        System.out.println("Assigned User ID: " + app.getUser().getUserId());
                                        System.out.println("--------------------------");
                                    }
                                }
                            } catch (SQLException e) {
                                System.out.println("Error fetching appointments: " + e.getMessage());
                            }
                            break;
    
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                    }
                     
                 }        

                System.out.println("Do you want to continue? (yes/no)");
                String continueInput = scanner.nextLine();
                if (!continueInput.equalsIgnoreCase("yes")) {
                    continueApp = false;
                    System.out.println("Exiting application. Goodbye!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
