package application.Repository;

import application.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vlad on 25-Mar-17.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByName(String name);

    Employee findByUsername(String username);
}
