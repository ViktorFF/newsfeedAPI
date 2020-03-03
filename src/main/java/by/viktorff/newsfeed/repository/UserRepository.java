package by.viktorff.newsfeed.repository;

import by.viktorff.newsfeed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByToken(String token);
    Optional<User> findUserByLogin(String login);
}
