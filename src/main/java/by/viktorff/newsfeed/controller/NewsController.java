package by.viktorff.newsfeed.controller;

import by.viktorff.newsfeed.exception.user.LoginUserException;
import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.apirequest.NewsApiRequest;
import by.viktorff.newsfeed.service.NewsService;
import by.viktorff.newsfeed.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/news")
@Validated
public class NewsController {
    private UserService userService;
    private NewsService newsService;

    public NewsController(UserService userService, NewsService newsService) {
        this.userService = userService;
        this.newsService = newsService;
    }

    @PostMapping
    public ResponseEntity<String> addNews(@Valid @RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        newsService.addNews(request.getNews());
        return ResponseEntity.ok("Successful operation");
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable @Min(value = 0) Long id,
                                            @RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(newsService.getNewsById(id));
    }

    @GetMapping(path = "/byTag")
    public ResponseEntity<List<News>> getNewsByTag(@RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(newsService.getNewsByTag(request.getTag()));
    }

    @GetMapping(path = "/byTags")
    public ResponseEntity<List<News>> getNewsByTags(@RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(newsService.getNewsByTags(request.getTags()));
    }

    @GetMapping(path = "/byAuthor")
    public ResponseEntity<List<News>> getNewsByAuthor(@RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(newsService.getNewsByAuthor(request.getAuthorId()));
    }

    @GetMapping(path = "/byStatus")
    public ResponseEntity<List<News>> getNewsByStatus(@RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        return ResponseEntity.ok().body(newsService.getNewsByStatus(request.getStatus()));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateNews(@PathVariable @Min(value = 0) Long id,
                                             @Valid @RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        if (!userService.isAdmin(request.getRequestUserRole()) || !userService.isModerator(request.getRequestUserRole()))
            ResponseEntity.badRequest().body("You don't have enough rights");
        newsService.updateNews(id, request.getNews());
        return ResponseEntity.ok("Successful operation");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable @Min(value = 0) Long id,
                                             @RequestBody NewsApiRequest request) {
        if (!userService.isLoggedIn(request.getToken())) throw new LoginUserException();
        if (!userService.isAdmin(request.getRequestUserRole()) || !userService.isModerator(request.getRequestUserRole()))
            ResponseEntity.badRequest().body("You don't have enough rights");
        newsService.deleteNews(id);
        return ResponseEntity.ok("Successful operation");
    }
}
