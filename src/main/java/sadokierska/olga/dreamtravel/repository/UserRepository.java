package sadokierska.olga.dreamtravel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sadokierska.olga.dreamtravel.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
