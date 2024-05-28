package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @PostMapping(path = "")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/me/travels")
    public ResponseEntity<List<Travel>> getUserTravelsByToken(OAuth2AuthenticationToken authentication) {
        String email = (String) authentication.getPrincipal().getAttributes().get("email");
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Travel> userTravels = user.getTravels();
            return ResponseEntity.ok(userTravels);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getUserInfo(OAuth2AuthenticationToken authentication) {
        String email = (String) authentication.getPrincipal().getAttributes().get("email");
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
