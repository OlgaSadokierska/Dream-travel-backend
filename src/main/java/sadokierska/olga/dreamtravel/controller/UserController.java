package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sadokierska.olga.dreamtravel.model.Travel;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.TravelRepository;
import sadokierska.olga.dreamtravel.repository.UserRepository;

import java.util.*;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelRepository travelRepository;



    @PostMapping(path="")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    /**@GetMapping("/{email}/travels")
    public ResponseEntity<List<Travel>> getUserTravelsByEmail(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Travel> userTravels = new ArrayList<>();

            Integer travelId = user.getTravel().getId();

            Optional<Travel> travelOptional = travelRepository.findById(travelId);

            if (travelOptional.isPresent()) {
                userTravels.add(travelOptional.get());
                return ResponseEntity.ok(userTravels);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/{email}")
    public ResponseEntity<Map<String, String>> getUserInfo(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("firstname", user.getFirstname());
            userInfo.put("lastname", user.getLastname());
            userInfo.put("email", user.getEmail());
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody Map<String, String> userData) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (userData.containsKey("firstname")) {
                user.setFirstname(userData.get("firstname"));
            }
            if (userData.containsKey("lastname")) {
                user.setLastname(userData.get("lastname"));
            }

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
