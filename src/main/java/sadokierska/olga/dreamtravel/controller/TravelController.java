package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sadokierska.olga.dreamtravel.model.Travel;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.TravelRepository;
import sadokierska.olga.dreamtravel.repository.UserRepository;

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
    private UserRepository userRepository;

    @GetMapping(path="")
    public ResponseEntity<List<Travel>> getAllTravels() {
        List<Travel> allTravels = travelRepository.findAll();
        return ResponseEntity.ok(allTravels);
    }
    @PostMapping(path="")
    public ResponseEntity<Travel> addNewTravel(@RequestBody Travel travel) {
        Travel newTravel = travelRepository.save(travel);
        return ResponseEntity.ok(newTravel);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Travel> addTravelForUser(
            @PathVariable Integer userId,
            @RequestBody Map<String, Object> travelData
    ) {
        try {
            // Pobieranie danych podróży z przesłanych danych
            String country = (String) travelData.get("country");
            String city = (String) travelData.get("city");
            Date startDate = (Date) travelData.get("startDate");
            Date endDate = (Date) travelData.get("endDate");
            String description = (String) travelData.get("description");
            int rate = (int) travelData.get("rate");

            // Tworzenie nowej podróży
            Travel newTravel = new Travel();
            newTravel.setCountry(country);
            newTravel.setCity(city);
            newTravel.setStartDate(startDate);
            newTravel.setEndDate(endDate);
            newTravel.setDescription(description);
            newTravel.setRate(rate);
            newTravel = travelRepository.save(newTravel);

            // Pobieranie danych użytkownika
            String firstname = (String) travelData.get("firstname");
            String lastname = (String) travelData.get("lastname");
            String email = (String) travelData.get("email");

            // Tworzenie nowego użytkownika
            User newUser = new User();
            newUser.setId(userId);
            newUser.setFirstname(firstname);
            newUser.setLastname(lastname);
            newUser.setEmail(email);
            newUser.setTravel(newTravel);
            userRepository.save(newUser);

            return ResponseEntity.ok(newTravel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}



