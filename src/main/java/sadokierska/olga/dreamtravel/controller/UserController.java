package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sadokierska.olga.dreamtravel.model.Travel;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.TravelRepository;
import sadokierska.olga.dreamtravel.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelRepository travelRepository;

   /* @GetMapping("/user")
    public Map<String, Object> user(OAuth2AuthenticationToken authentication) {
        return authentication.getPrincipal().getAttributes();
    }*/

    @PostMapping(path="")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{email}/travels")
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
    }

}
