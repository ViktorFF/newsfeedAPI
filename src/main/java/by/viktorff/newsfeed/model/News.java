package by.viktorff.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @NotNull
    @PositiveOrZero
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;
    private List<String> imageUrls;

    @NotBlank
    private User author;
    private NewsTag tag;
    private NewsStatus status;
}
