package by.viktorff.newsfeed.model.apirequest;

import by.viktorff.newsfeed.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApiRequest {
    private String token;
    private User user;
}
