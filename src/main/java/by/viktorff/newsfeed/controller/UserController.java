package by.viktorff.newsfeed.controller;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.LoginUserException;
import by.viktorff.newsfeed.model.User;
import by.viktorff.newsfeed.model.apirequest.UserApiRequest;
import by.viktorff.newsfeed.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping(path = "/user")
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable @Min(value = 0) Long id,
                                        @RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateUser(@PathVariable @Min(value = 0) Long id,
                                             @Valid @RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        if (userService.isAdmin(request.getRequestUserRole())) {
            userService.updateUser(id, request.getUser());
            log.info("User with id={} was updated", id);
            return ResponseEntity.ok("Successful operation");
        }
        return ResponseEntity.badRequest().body("You don't have enough rights");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Min(value = 0) Long id,
                                             @RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        if (!userService.isAdmin(request.getRequestUserRole())) ResponseEntity.badRequest().body("You don't have enough rights");
        userService.deleteUser(id, request.getToken());
        log.info("User with id={} was removed", id);
        return ResponseEntity.ok("Successful operation");
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody UserApiRequest request) {
        String token = userService.authentication(request.getUser());
        if (token == null) throw new AuthenticationUserException("Invalid login/password supplied");
        log.info("User successfully logged in");
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(path = "logout")
    public ResponseEntity<String> logout(@RequestBody UserApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        userService.logout(request.getToken());
        log.info("User successfully logged out");
        return ResponseEntity.ok("Successful operation");
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        log.info("User successfully created");
        return ResponseEntity.ok("Successful operation");
    }
}
