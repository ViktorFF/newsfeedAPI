package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.DeleteUserException;
import by.viktorff.newsfeed.exception.user.UserNotFoundException;
import by.viktorff.newsfeed.model.Role;
import by.viktorff.newsfeed.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class UserServiceImpl implements UserService{
    private Map<Long, User> usersMap;
    private Map<String, User> authMap;
    private Map<Long, String> tokens;

    public UserServiceImpl(Map<Long, User> usersMap, Map<String, User> authMap, Map<Long, String> tokens) {
        this.usersMap = usersMap;
        this.authMap = authMap;
        this.tokens = tokens;
    }

    public void addUser(User user) {
        usersMap.put(user.getId(), user);
        authMap.put(user.getLogin(), user);
    }

//    public void addAllUsers(List<User> userList) {
//        for (User user: userList) {
//            usersMap.put(user.getId(), user);
//            authMap.put(user.getLogin(), user);
//        }
//    }

    public String authentication(User newUser) {
        if (tokens.containsKey(newUser.getId())) throw new AuthenticationUserException("User has already logged in");
        String login = newUser.getLogin();
        String password = newUser.getPassword();
        if (usersMap.isEmpty()) {
            return null;
        }
        if (authMap.containsKey(login)) {
            if (authMap.get(login).getPassword().equals(password)) {
                String token = Integer.toString(new Random().nextInt());
                tokens.put(authMap.get(login).getId(), token);
                return token;
            }
        }
        return null;
    }

    public void logout(String token) {
        tokens.values().remove(token);
    }

    public boolean isLoggedIn(String token) {
        return tokens.containsValue(token);
    }

    @Override
    public boolean isAdmin(Role role) {
        return role.equals(Role.ADMIN);
    }

    @Override
    public boolean isModerator(Role role) {
        return role.equals(Role.MODERATOR);
    }

    public User getUser(Long id) {
        if (!usersMap.containsKey(id)) throw new UserNotFoundException();
        return usersMap.get(id);
    }

    public User getUser(String token) {
        if (!tokens.containsValue(token)) throw new UserNotFoundException();
        for (Map.Entry<Long, String> users : tokens.entrySet()) {
            if (users.getValue().equals(token)) {
                return usersMap.get(users.getKey());
            }
        }
        return null;
    }

    public void updateUser(Long id, User user) {
        if (!usersMap.containsKey(id)) throw new UserNotFoundException();
        if (user.getId() != id) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        usersMap.put(id, user);
    }

    public void deleteUser(Long id, String token) {
        if (!usersMap.containsKey(id)) throw new UserNotFoundException();
        if (tokens.get(id).equals(token)) throw new DeleteUserException();
        usersMap.remove(id);
    }
}
