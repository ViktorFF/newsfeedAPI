package by.viktorff.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @NotNull
    @PositiveOrZero
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @ElementCollection
    private List<String> imageUrls;

    @NotBlank
    @OneToOne(cascade = CascadeType.ALL)
    private User author;
    private NewsTag tag;
    private NewsStatus status;
}
