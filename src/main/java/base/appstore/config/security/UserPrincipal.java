package base.appstore.config.security;

import base.appstore.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserPrincipal {

    private long id;
    private String username;
    private String email;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.email = user.getEmail();
    }
}
