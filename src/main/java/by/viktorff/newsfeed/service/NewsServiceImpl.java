package by.viktorff.newsfeed.service;

import by.viktorff.newsfeed.exception.news.NewsNotFoundException;
import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.NewsStatus;
import by.viktorff.newsfeed.model.NewsTag;
import by.viktorff.newsfeed.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class NewsServiceImpl implements NewsService{
    private Map<Long, News> allNews;

    @Autowired
    public NewsServiceImpl(Map<Long, News> allNews) {
        this.allNews = allNews;
    }

    public void addNews(News news) {
        allNews.put(news.getId(), news);
    }

    public News getNewsById(Long id) {
        if (!allNews.containsKey(id)) throw new NewsNotFoundException();
        return allNews.get(id);
    }

    public void updateNews(Long id, News news) {
        if (!allNews.containsKey(id)) throw new NewsNotFoundException();
        if (news.getId() != id) throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        allNews.put(news.getId(), news);
    }

    public void deleteNews(Long id) {
        if (!allNews.containsKey(id)) throw new NewsNotFoundException();
        allNews.remove(id);
    }

    public List<News> getNewsByTag(NewsTag tag) {
        List<News> newsList = new ArrayList<>();
        for (News news : allNews.values()) {
            if (news.getTag().equals(tag)) {
                newsList.add(news);
            }
        }
        return newsList;
    }

    public List<News> getNewsByTags(List<NewsTag> tags) {
        List<News> newsList = new ArrayList<>();
        for (News news : allNews.values()) {
            if (tags.contains(news.getTag())) {
                newsList.add(news);
            }
        }
        return newsList;
    }

    public List<News> getNewsByAuthor(User author) {
        List<News> newsList = new ArrayList<>();
        for (News news : allNews.values()) {
            if (news.getAuthor().equals(author)) {
                newsList.add(news);
            }
        }
        return newsList;
    }

    public List<News> getNewsByStatus(NewsStatus status) {
        List<News> newsList = new ArrayList<>();
        for (News news : allNews.values()) {
            if (news.getStatus().equals(status)) {
                newsList.add(news);
            }
        }
        return newsList;
    }
}
