package base.appstore.controller.dto;

import base.appstore.model.App;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDto {

    private Long id;
    private String title;
    private String description;
    private long views;

    public AppDto(App app) {
        this.id = app.getId();
        this.title = app.getTitle();
        this.description = app.getDescription();
        this.views = app.getViews();
    }

    public App toEntity() {
        final App app = new App();
        app.setTitle(title);
        app.setDescription(description);
        return app;
    }

}
