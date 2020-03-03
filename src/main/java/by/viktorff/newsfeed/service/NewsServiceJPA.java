package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.exception.news.NewsNotFoundException;
import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.NewsStatus;
import by.viktorff.newsfeed.model.NewsTag;
import by.viktorff.newsfeed.model.User;
import by.viktorff.newsfeed.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceJPA implements NewsService {
    private NewsRepository newsRepository;

    @Autowired
    public NewsServiceJPA(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public NewsServiceJPA() {
    }

    public void addNews(News news) {
        newsRepository.save(news);
    }

    public News getNewsById(Long id) {
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new NewsNotFoundException();
    }

    public List<News> getNewsByTag(NewsTag tag) {
        return newsRepository.findAllByTag(tag);
    }

    public List<News> getNewsByTags(List<NewsTag> tags) {
        List<News> newsList = new ArrayList<>();
        for (NewsTag tag: tags) {
            newsList.addAll(newsRepository.findAllByTag(tag));
        }
        return newsList;
    }

    public List<News> getNewsByAuthor(User author) {
        return newsRepository.findAllByAuthor(author);
    }

    public List<News> getNewsByStatus(NewsStatus status) {
        return newsRepository.findAllByStatus(status);
    }

    public void updateNews(Long id, News news) {
        Optional<News> byId = newsRepository.findById(id);
        if (news.getId() != id) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        if (byId.isPresent()) {
            newsRepository.save(news);
        }
        throw new NewsNotFoundException();
    }

    public void deleteNews(Long id) {
        Optional<News> byId = newsRepository.findById(id);
        if (byId.isPresent()) {
            newsRepository.deleteById(id);
        }
        throw new NewsNotFoundException();
    }
}
