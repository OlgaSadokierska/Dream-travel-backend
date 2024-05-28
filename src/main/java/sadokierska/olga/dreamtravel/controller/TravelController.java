package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sadokierska.olga.dreamtravel.model.Travel;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.TravelRepository;
import sadokierska.olga.dreamtravel.repository.UserRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/travels")
@CrossOrigin(origins = "http://localhost:3000")
public class TravelController {

    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private UserRepository userRepository;

    /*
     * @GetMapping(path="")
     * public ResponseEntity<List<Travel>> getAllTravels() {
     * List<Travel> allTravels = travelRepository.findAll();
     * return ResponseEntity.ok(allTravels);
     * }
     */
    @GetMapping("")
    public List<Map<String, Object>> getAllTravels() {
        List<Object[]> results = travelRepository.findAllTravelsInfo();
        return results.stream()
                .map(row -> {
                    Map<String, Object> map = Map.of(
                            "id", (Integer) row[0],
                            "country", (String) row[1],
                            "city", (String) row[2],
                            "startDate", row[3],
                            "endDate", row[4],
                            "description", (String) row[5],
                            "rate", (int) row[6],
                            "userId", (Integer) row[7]);
                    return map;
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTravel(OAuth2AuthenticationToken authentication, @RequestBody Travel newTravel) {
        String email = (String) authentication.getPrincipal().getAttributes().get("email");
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Użytkownik o podanym adresie e-mail nie istnieje");
        }

        try {
            newTravel.setUser(user);
            travelRepository.save(newTravel);
            return ResponseEntity.status(HttpStatus.CREATED).body("Podróż dodana pomyślnie");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nie udało się dodać podróży");
        }
    }

    @PutMapping("/{travelId}")
    public ResponseEntity<String> updateTravel(@PathVariable Integer travelId, @RequestBody Travel updatedTravel) {
        try {

            if (!travelRepository.existsById(travelId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Podróż o podanym ID nie istnieje");
            }

            Travel travel = travelRepository.findById(travelId).get();

            travel.setCountry(updatedTravel.getCountry());
            travel.setCity(updatedTravel.getCity());
            travel.setStartDate(updatedTravel.getStartDate());
            travel.setEndDate(updatedTravel.getEndDate());
            travel.setDescription(updatedTravel.getDescription());
            travel.setRate(updatedTravel.getRate());

            travelRepository.save(travel);

            return ResponseEntity.status(HttpStatus.OK).body("Podróż została zaktualizowana");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nie udało się zaktualizować podróży");
        }
    }

    @DeleteMapping("/{travelId}")
    public ResponseEntity<String> deleteTravel(@PathVariable Integer travelId) {
        try {

            if (!travelRepository.existsById(travelId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Podróż o podanym ID nie istnieje");
            }
            travelRepository.deleteById(travelId);

            return ResponseEntity.status(HttpStatus.OK).body("Podróż została usunięta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Nie udało się usunąć podróży");
        }
    }
}
