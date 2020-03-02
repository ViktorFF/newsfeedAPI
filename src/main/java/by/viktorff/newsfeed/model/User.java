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
public class User {

    @Id
    @NotNull
    @PositiveOrZero
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Role role;
    private String firstName;
    private String lastName;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private List<NewsTag> newsTagList;
    private String email;

    @NotBlank
    private String login;
    @NotBlank
    private String password;
    private String token;
    private String phone;
}
