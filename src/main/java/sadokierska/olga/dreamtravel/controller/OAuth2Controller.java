package sadokierska.olga.dreamtravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import sadokierska.olga.dreamtravel.model.User;
import sadokierska.olga.dreamtravel.repository.UserRepository;

import java.util.Optional;

@RestController
public class OAuth2Controller {

    private final UserRepository userRepository;

    @Autowired
    public OAuth2Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login/oauth2/code/google")
    public RedirectView loginSuccess(@PathVariable String provider, OAuth2AuthenticationToken authenticationToken) {

        String userEmail = (String) authenticationToken.getPrincipal().getAttributes().get("email");

        Optional<User> existingUser = userRepository.findByEmail(userEmail);
        if (!existingUser.isPresent()) {
            User user = new User();
            user.setEmail(userEmail);
            user.setProvider(provider);
            userRepository.save(user);
        }

        return new RedirectView("/login-success");
    }

    @GetMapping
    public Object sayHello(Authentication authentication) {
        return authentication.getPrincipal();
    }
}