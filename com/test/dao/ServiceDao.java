package dao;

import entity.Service;
import java.sql.SQLException;
import java.util.List;

public interface ServiceDao {
    void createService(Service service) throws SQLException;
    Service getServiceById(int serviceId) throws SQLException;
    List<Service> getAllServices() throws SQLException;
    void updateService(Service service) throws SQLException;
    void deleteService(int serviceId) throws SQLException;
}
