package by.viktorff.newsfeed.repository;

import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.NewsStatus;
import by.viktorff.newsfeed.model.NewsTag;

import by.viktorff.newsfeed.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByTag(NewsTag tag);
    List<News> findAllByAuthor(@NotBlank User author);
    List<News> findAllByStatus(NewsStatus status);
}
