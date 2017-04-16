package application.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import application.Entity.Client;

/**
 * Created by Vlad on 21-Mar-17.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Client findByName(String name);

}
