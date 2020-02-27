package by.viktorff.newsfeed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class NewsfeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsfeedApplication.class, args);
        log.info("Application started");
    }

}
