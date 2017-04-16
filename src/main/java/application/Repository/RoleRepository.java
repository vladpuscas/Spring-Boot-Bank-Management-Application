package application.Repository;

import application.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Vlad on 01-Apr-17.
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRole(String role);
}
