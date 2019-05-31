package base.appstore.controller.dto;

import base.appstore.model.App;
import base.appstore.model.Rating;
import base.appstore.model.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AppDto {

    private Long id;
    private String title;
    private String description;
    private List<ScreenshotDto> screenshots = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private UserDto author;
    private double rating;
    private long updateDate;
    private long creationDate;
    private long views;

    public AppDto(App app) {
        this.id = app.getId();
        this.title = app.getTitle();
        this.description = app.getDescription();
        this.views = app.getViews();
        this.screenshots = app.getScreenshots().stream().map(ScreenshotDto::new).collect(Collectors.toList());
        this.tags = app.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        this.updateDate = app.getUpdateDate().getTime();
        this.creationDate = app.getCreateDate().getTime();
        this.author = new UserDto(app.getUser());
        this.rating = app.getRatings().stream().mapToDouble(Rating::getStars).average().orElse(0);
    }

    public App toEntity() {
        final App app = new App();
        app.setTitle(title);
        app.setDescription(description);
        tags.stream().map(t -> new Tag(null, t)).forEach(app.getTags()::add);
        return app;
    }

}
