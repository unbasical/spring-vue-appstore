package base.appstore.controller.dto;

import base.appstore.model.Screenshot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenshotDto {

    private Long id;

    public ScreenshotDto(Screenshot s) {
        this.id = s.getId();
    }

}
