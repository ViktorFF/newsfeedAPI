package by.viktorff.newsfeed.config;

import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Map<Long, User> getUsersMap() {
        return new LinkedHashMap<>();
    }

    @Bean
    public Map<String, User> getAuthMap() {
        return new LinkedHashMap<>();
    }

    @Bean
    public Map<Long, String> getUserTokens() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, News> getAllNews() {
        return new LinkedHashMap<>();
    }
}
