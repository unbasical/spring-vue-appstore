package base.appstore.controller.dto;

import base.appstore.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public User toEntity() {
        final User user = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}
