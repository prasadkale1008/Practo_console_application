package daoImple;

import dao.ServiceDao;
import entity.Service;
import storage.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {

    // Use the existing connection logic from DatabaseConnection
    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public void createService(Service service) throws SQLException {
        String query = "INSERT INTO services (name, description, price) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setBigDecimal(3, service.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error creating service: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Service getServiceById(int serviceId) throws SQLException {
        String query = "SELECT * FROM services WHERE service_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Service service = new Service();
                service.setServiceId(rs.getInt("service_id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getBigDecimal("price"));
                return service;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving service: " + e.getMessage());
            throw e;
        }
        return null;  // Return null if no service is found
    }

    @Override
    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        // Update the column names to match your database schema
        String query = "SELECT service_id, service_name, description, price FROM services"; 
    
        try (Connection connection = connect(); 
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
    
            System.out.println("Executing query: " + query); // Debugging line
    
            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceId(resultSet.getInt("service_id"));
                service.setName(resultSet.getString("service_name")); // Update this based on actual column name
                service.setDescription(resultSet.getString("description")); // Update this if needed
                service.setPrice(resultSet.getBigDecimal("price"));
                services.add(service);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all services: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for more details
            throw e; 
        }
    
        return services;
    }
    


    @Override
    public void updateService(Service service) throws SQLException {
        String query = "SELECT service_id, service_name, service_description, service_price FROM services"; 

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, service.getName());
            stmt.setString(2, service.getDescription());
            stmt.setBigDecimal(3, service.getPrice());
            stmt.setInt(4, service.getServiceId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteService(int serviceId) throws SQLException {
        String query = "DELETE FROM services WHERE service_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
            throw e;
        }
    }
}
