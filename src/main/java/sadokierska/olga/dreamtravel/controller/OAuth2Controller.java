package sadokierska.olga.dreamtravel.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.UserRepository;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class OAuth2Controller {

    private final UserRepository userRepository;


    @GetMapping("")
    public Object sayHello(OAuth2AuthenticationToken authentication) {
        String userEmail = (String) authentication.getPrincipal().getAttributes().get("email");
        String firstname = (String) authentication.getPrincipal().getAttributes().get("given_name");
        String lastname = (String) authentication.getPrincipal().getAttributes().get("family_name");

        Optional<User> existingUser = userRepository.findByEmail(userEmail);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(userEmail);
            user.setLastname(lastname);
            user.setFirstname(firstname);
            userRepository.save(user);
        }

        return authentication.getPrincipal();
    }
}