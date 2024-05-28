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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/travels")
@CrossOrigin(origins = "http://localhost:3000")
public class TravelController {

    @Autowired
    private TravelRepository travelRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping(path="")
    public ResponseEntity<List<Travel>> getAllTravels() {
        List<Travel> allTravels = travelRepository.findAll();
        return ResponseEntity.ok(allTravels);
    }
    @PostMapping("/add/{email}")
    public ResponseEntity<String> addTravel(@PathVariable String email, @RequestBody Travel newTravel) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik o podanym adresie e-mail nie istnieje");
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





