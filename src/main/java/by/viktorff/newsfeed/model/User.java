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
public class User {

    @NotNull
    @PositiveOrZero
    private long id;
    private Role role;
    private String firstName;
    private String lastName;
    private List<NewsTag> newsTagList;
    private String email;

    @NotBlank
    private String login;
    @NotBlank
    private String password;
    private String phone;
}
