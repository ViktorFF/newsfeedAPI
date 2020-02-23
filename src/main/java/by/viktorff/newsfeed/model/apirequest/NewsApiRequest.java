package by.viktorff.newsfeed.model.apirequest;

import by.viktorff.newsfeed.model.News;
import by.viktorff.newsfeed.model.NewsStatus;
import by.viktorff.newsfeed.model.NewsTag;
import by.viktorff.newsfeed.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsApiRequest {
    private String token;
    private Role requestUserRole;
    private News news;
    private NewsTag tag;
    private NewsStatus status;
    private Long authorId;
    private List<NewsTag> tags;
}
