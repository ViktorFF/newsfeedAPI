package by.viktorff.newsfeed.controller;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.DeleteUserException;
import by.viktorff.newsfeed.exception.user.LoginUserException;
import by.viktorff.newsfeed.model.User;
import by.viktorff.newsfeed.model.apirequest.UserApiRequest;
import by.viktorff.newsfeed.service.UserService;
import by.viktorff.newsfeed.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{login}")
    public ResponseEntity<User> getUser(@PathVariable String login,
                                        @RequestBody UserApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(userService.getUser(login));
    }

    @PutMapping(path = "/{login}")
    public ResponseEntity<String> updateUser(@PathVariable String login,
                                             @Valid @RequestBody UserApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        userService.updateUser(login, request.getUser());
        return ResponseEntity.ok("Successful operation");
    }

    @DeleteMapping(path = "/{login}")
    public ResponseEntity<String> deleteUser(@PathVariable String login,
                                                  @RequestBody UserApiRequest request) {
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        if (userService.getTokens().get(userService.getUsersMap().get(login).getId()).equals(request.getToken())) throw new DeleteUserException();
        userService.deleteUser(login);
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
        if (!userService.getTokens().containsValue(request.getToken())) throw new LoginUserException();
        userService.logout(request.getToken());
        return ResponseEntity.ok("Successful operation");
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("Successful operation");
    }

    @PostMapping(path = "/createWithList")
    public ResponseEntity<String> createWithList(@Valid @RequestBody List<User> users) {
        userService.addAllUsers(users);
        return ResponseEntity.ok("Successful operation");
    }
}
