package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, User> getUsersMap();
    Map<Long, String> getTokens();
    void addUser(User user);
    void addAllUsers(List<User> userList);
    User getUser(String login);
    void updateUser(String login, User user);
    void deleteUser(String login);
    String authentication(User newUser);
    void logout(String token);
}
