package by.viktorff.newsfeed.model.apirequest;

import by.viktorff.newsfeed.model.Role;
import by.viktorff.newsfeed.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApiRequest {
    private String token;
    private Role requestUserRole;
    private User user;
}
