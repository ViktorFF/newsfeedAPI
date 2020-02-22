package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.UserNotFoundException;
import by.viktorff.newsfeed.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class UserServiceImpl implements UserService{
    private Map<String, User> usersMap;
    private Map<Long, String> tokens;

    public UserServiceImpl(Map<String, User> usersMap, Map<Long, String> tokens) {
        this.usersMap = usersMap;
        this.tokens = tokens;
    }

    public Map<String, User> getUsersMap() {
        return usersMap;
    }

    public Map<Long, String> getTokens() {
        return tokens;
    }

    public void addUser(User user) {
        usersMap.put(user.getLogin(), user);
    }

    public void addAllUsers(List<User> userList) {
        for (User user: userList) {
            usersMap.put(user.getLogin(), user);
        }
    }

    public String authentication(User newUser) {
        if (tokens.containsKey(newUser.getId())) throw new AuthenticationUserException("User has already logged in");
        String login = newUser.getLogin();
        String password = newUser.getPassword();
        if (usersMap.isEmpty()) {
            return null;
        }
        if (usersMap.containsKey(login)) {
            if (usersMap.get(login).getPassword().equals(password)) {
                String token = Integer.toString(new Random().nextInt());
                tokens.put(usersMap.get(login).getId(), token);
                return token;
            }
        }
        return null;
    }

    public void logout(String token) {
        tokens.values().remove(token);
    }

    public User getUser(String login) {
        if (!usersMap.containsKey(login)) throw new UserNotFoundException();
        return usersMap.get(login);
    }

    public void updateUser(String login, User user) {
        if (!usersMap.containsKey(login)) throw new UserNotFoundException();
        if (!user.getLogin().equals(login)) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        usersMap.put(login, user);
    }

    public void deleteUser(String login) {
        if (!usersMap.containsKey(login)) throw new UserNotFoundException();
        usersMap.remove(login);
    }
}
