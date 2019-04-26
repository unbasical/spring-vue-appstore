package base.appstore.controller.dto;

import base.appstore.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

    private Long id;
    private int stars;
    private Date createDate;
    private UserDto author;

    public RatingDto(Rating rating) {
        this.id = rating.getId();
        this.stars = rating.getStars();
        this.createDate = rating.getCreateDate();
        this.author = new UserDto(rating.getAuthor());
    }

    public Rating toEntity() {
        final Rating rating = new Rating();
        rating.setStars(stars);
        rating.setAuthor(author.toEntity());
        return rating;
    }
}
