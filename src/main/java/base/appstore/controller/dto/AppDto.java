package base.appstore.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppDto {

    private Long id;
    private String title;
    private String description;
    private long views;

}
