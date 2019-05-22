package base.appstore.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class JWTTokenResponse {
    private Long id;
    private String name;
    private String email;
    private String token;
}
