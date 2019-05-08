package base.appstore.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String title;
    private String description;
    @EqualsAndHashCode.Exclude
    private long views;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    private Date createDate;
    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private List<Screenshot> screenshots = new ArrayList<>();

    @OneToOne(cascade = CascadeType.MERGE)
    @EqualsAndHashCode.Exclude
    private Logo logo;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.retainAll(tags);
        this.tags.addAll(tags);
    }

    public void setComments(List<Comment> comments) {
        this.comments.retainAll(comments);
        this.comments.addAll(comments);
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings.retainAll(ratings);
        this.ratings.addAll(ratings);
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots.retainAll(screenshots);
        this.screenshots.addAll(screenshots);
    }
}
