package by.viktorff.newsfeed.controller;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.LoginUserException;
import by.viktorff.newsfeed.model.User;
import by.viktorff.newsfeed.model.apirequest.UserApiRequest;
import by.viktorff.newsfeed.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id,
                                        @RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @Valid @RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        if (userService.isAdmin(request.getRequestUserRole()) || userService.getUser(request.getToken()).getId() == id) {
            userService.updateUser(id, request.getUser());
            return ResponseEntity.ok("Successful operation");
        }
        return ResponseEntity.badRequest().body("You don't have enough rights");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id,
                                             @RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        if (!userService.isAdmin(request.getRequestUserRole())) ResponseEntity.badRequest().body("You don't have enough rights");
        userService.deleteUser(id, request.getToken());
        return ResponseEntity.ok("Successful operation");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody UserApiRequest request) {
        String token = userService.authentication(request.getUser());
        if (token == null) throw new AuthenticationUserException("Invalid login/password supplied");
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<String> logout(@RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        userService.logout(request.getToken());
        return ResponseEntity.ok("Successful operation");
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("Successful operation");
    }
}
