package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUssers = userRepository.findAll();
        return ResponseEntity.ok(allUssers);
    }

    @PostMapping(path="")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }
}
