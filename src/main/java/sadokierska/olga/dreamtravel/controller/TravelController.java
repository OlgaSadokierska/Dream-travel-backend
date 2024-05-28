package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
            // Sprawdzenie czy wszystkie wymagane pola są dostępne
            if (!travelData.containsKey("country") || !travelData.containsKey("city") ||
                    !travelData.containsKey("startDate") || !travelData.containsKey("endDate") ||
                    !travelData.containsKey("description") || !travelData.containsKey("rate")) {
                System.err.println("Missing required fields in travel data"); // Komunikat o braku wymaganych danych
                return ResponseEntity.badRequest().body(null); // Brak wszystkich wymaganych danych
            }

            // Pobranie danych podróży z przesłanych danych
            String country = (String) travelData.get("country");
            String city = (String) travelData.get("city");
            LocalDate startDate = LocalDate.parse((String) travelData.get("startDate"));
            LocalDate endDate = LocalDate.parse((String) travelData.get("endDate"));
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
            travelRepository.save(newTravel);

            // Sprawdzenie czy użytkownik istnieje
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Przypisanie danych użytkownika do nowego użytkownika
                User newUser = new User();
                newUser.setFirstname(user.getFirstname());
                newUser.setLastname(user.getLastname());
                newUser.setEmail(user.getEmail());
                newUser.setTravel(newTravel);
                userRepository.save(newUser);

                return ResponseEntity.ok(newTravel);
            } else {
                System.err.println("User not found with ID: " + userId); // Komunikat o braku użytkownika
                return ResponseEntity.notFound().build(); // Użytkownik nie został znaleziony
            }
        } catch (Exception e) {
            System.err.println("An error occurred while adding travel for user: " + e.getMessage()); // Komunikat o błędzie
            return ResponseEntity.badRequest().body(null); // Inny błąd
        }
    }


}



