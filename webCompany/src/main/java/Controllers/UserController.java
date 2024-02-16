package Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.webcompany.entites.Authentification;
import com.example.webcompany.entites.User;
import com.example.webcompany.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void create(@RequestBody User user) throws Exception {
        System.out.println(user.toString());
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
    public boolean authentification(@RequestBody Authentification auth) {
        return userService.authentification(auth.getEmail(), auth.getPassword(), auth.isFirstTime());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/forgotpassword")
    public boolean updatePassword(@RequestBody Map<String, String> credentials) {
        return userService.updatePassword(credentials.get("email"), credentials.get("password"));
    }

}
