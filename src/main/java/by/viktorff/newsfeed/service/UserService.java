package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.model.Role;
import by.viktorff.newsfeed.model.User;

public interface UserService {
    void addUser(User user);
    User getUser(Long id);
    void updateUser(Long id, User user);
    void deleteUser(Long id, String token);
    String authentication(User newUser);
    void logout(String token);
    boolean isLoggedIn(String token);
    boolean isAdmin(Role role);
    boolean isModerator(Role role);
}
