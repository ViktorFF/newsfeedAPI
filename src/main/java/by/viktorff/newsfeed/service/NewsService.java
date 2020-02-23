package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.NewsStatus;
import by.viktorff.newsfeed.model.NewsTag;

import java.util.List;

public interface NewsService {
    void addNews(News news);
    News getNewsById(Long id);
    List<News> getNewsByTag(NewsTag tag);
    List<News> getNewsByTags(List<NewsTag> tags);
    List<News> getNewsByAuthor(Long authorId);
    List<News> getNewsByStatus(NewsStatus status);
    void updateNews(Long id, News news);
    void deleteNews(Long id);
}
