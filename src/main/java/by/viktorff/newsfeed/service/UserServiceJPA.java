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

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceJPA implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceJPA() {
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new UserNotFoundException();
    }

    public User getUser(String token) {
        Optional<User> byToken = userRepository.findUserByToken(token);
        if (byToken.isPresent()) {
            return byToken.get();
        }
        throw new UserNotFoundException();
    }

    public void updateUser(Long id, User user) {
        if (user.getId() != id) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.save(user);
        } else throw new UserNotFoundException();
    }

    public void deleteUser(Long id, String token) {
        Optional<User> byId = userRepository.findById(id);
        Optional<User> byToken = userRepository.findUserByToken(token);
        if (byToken.isPresent()) {
            if (byToken.get().getToken().equals(token)) throw new DeleteUserException();
        }
        if (byId.isPresent()) {
            userRepository.deleteById(id);
        } else throw new UserNotFoundException();
    }

    public String authentication(User newUser) {
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
        Optional<User> byToken = userRepository.findUserByToken(token);
        if (byToken.isPresent()) {
            User user = byToken.get();
            user.setToken(null);
            userRepository.save(user);
        }
    }

    public boolean isLoggedIn(String token) {
        Optional<User> byToken = userRepository.findUserByToken(token);
        return byToken.isPresent();
    }

    public boolean isAdmin(Role role) {
        return role.equals(Role.ADMIN);
    }

    public boolean isModerator(Role role) {
        return role.equals(Role.MODERATOR);
    }
}
