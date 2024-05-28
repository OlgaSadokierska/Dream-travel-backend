package sadokierska.olga.dreamtravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sadokierska.olga.dreamtravel.model.Travel;

import java.util.List;
import java.util.UUID;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer>{
    @Query(value = "SELECT t.id, t.country, t.city, t.start_date, t.end_date, t.description, t.rate, t.user_id FROM travel t", nativeQuery = true)
    List<Object[]> findAllTravelsInfo();

}
