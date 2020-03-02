package by.viktorff.newsfeed.repository;

import by.viktorff.newsfeed.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
