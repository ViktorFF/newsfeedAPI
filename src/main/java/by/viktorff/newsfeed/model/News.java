package by.viktorff.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private Long id;
    private String title;
    private String text;
    private List<String> imageUrls;
    private NewsStatus status;
}
