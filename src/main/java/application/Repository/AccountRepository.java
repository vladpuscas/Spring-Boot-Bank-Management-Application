package application.Repository;

import application.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vlad on 22-Mar-17.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {


}
