package Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.webcompany.entites.Authentification;
import com.example.webcompany.entites.User;
import com.example.webcompany.security.JwtService;
import com.example.webcompany.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody User user) throws Exception {
        this.userService.creer(user);
    }

    @GetMapping("/alluser")
    public List<User> rechercher() {
        return this.userService.rechercher();
    }

    @GetMapping("/user/{id}")
    public User lire(@PathVariable int id) {
        return this.userService.lire(id);
    }

    @PostMapping("/authentification")
    public Map<String, String> authentification(@RequestBody Authentification auth) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
        if (authentication.isAuthenticated()) {
            userService.updateFirstTime(auth.getEmail(), false);
            return this.jwtService.generate(auth.getEmail());
        }
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/forgotpassword")
    public boolean updatePassword(@RequestBody Map<String, String> credentials) {
        return userService.updatePassword(credentials.get("email"), credentials.get("password"));
    }

}
