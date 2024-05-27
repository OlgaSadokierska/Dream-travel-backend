package sadokierska.olga.dreamtravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sadokierska.olga.dreamtravel.model.Travel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {
    Optional<Travel> findById(Integer travelId);
}
