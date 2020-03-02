package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.exception.user.AuthenticationUserException;
import by.viktorff.newsfeed.exception.user.DeleteUserException;
import by.viktorff.newsfeed.exception.user.UserNotFoundException;
import by.viktorff.newsfeed.model.Role;
import by.viktorff.newsfeed.model.User;
import by.viktorff.newsfeed.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
//    private Map<Long, User> usersMap;
//    private Map<String, User> authMap;
//    private Map<Long, String> tokens;
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public String authentication(User newUser) {
//        if (tokens.containsKey(newUser.getId())) throw new AuthenticationUserException("User has already logged in");
//        String login = newUser.getLogin();
//        String password = newUser.getPassword();
//        if (usersMap.isEmpty()) {
//            return null;
//        }
//        if (authMap.containsKey(login)) {
//            if (authMap.get(login).getPassword().equals(password)) {
//                String token = Integer.toString(new Random().nextInt());
//                tokens.put(authMap.get(login).getId(), token);
//                return token;
//            }
//        }
//        return null;
        Optional<User> byLogin = userRepository.findUserByLogin(newUser.getLogin());
        if (byLogin.isPresent()) {
            if (byLogin.get().getToken() != null) throw new AuthenticationUserException("User has already logged in");
            if (byLogin.get().getPassword().equals(newUser.getPassword())) {
                String token = Integer.toString(new Random().nextInt());
                User user = byLogin.get();
                user.setToken(token);
                userRepository.save(user);
                return token;
            }
        }
        return null;
    }

    public void logout(String token) {
//        tokens.values().remove(token);
        Optional<User> byToken = userRepository.findUserByToken(token);
        if (byToken.isPresent()) {
            User user = byToken.get();
            user.setToken(null);
            userRepository.save(user);
        }
    }

    public boolean isLoggedIn(String token) {
//        return tokens.containsValue(token);
        Optional<User> byToken = userRepository.findUserByToken(token);
        return byToken.isPresent();
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
//        if (!usersMap.containsKey(id)) throw new UserNotFoundException();
//        return usersMap.get(id);
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new UserNotFoundException();
    }

    public User getUser(String token) {
//        if (!tokens.containsValue(token)) throw new UserNotFoundException();
//        for (Map.Entry<Long, String> users : tokens.entrySet()) {
//            if (users.getValue().equals(token)) {
//                return usersMap.get(users.getKey());
//            }
//        }
//        return null;
        Optional<User> byToken = userRepository.findUserByToken(token);
        if (byToken.isPresent()) {
            return byToken.get();
        }
        throw new UserNotFoundException();
    }

    public void updateUser(Long id, User user) {
//        if (user.getId() != id) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
//        usersMap.put(id, user);
        if (user.getId() != id) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.save(user);
        } else throw new UserNotFoundException();
    }

    public void deleteUser(Long id, String token) {
//        if (!usersMap.containsKey(id)) throw new UserNotFoundException();
//        if (tokens.get(id).equals(token)) throw new DeleteUserException();
//        usersMap.remove(id);
        Optional<User> byId = userRepository.findById(id);
        Optional<User> byToken = userRepository.findUserByToken(token);
        if (byToken.isPresent()) {
            if (byToken.get().getToken().equals(token)) throw new DeleteUserException();
        }
        if (byId.isPresent()) {
            userRepository.deleteById(id);
        } else throw new UserNotFoundException();
    }
}
