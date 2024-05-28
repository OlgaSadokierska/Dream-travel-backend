package sadokierska.olga.dreamtravel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sadokierska.olga.dreamtravel.model.Travel;
import sadokierska.olga.dreamtravel.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

}
