package sadokierska.olga.dreamtravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sadokierska.olga.dreamtravel.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
}
