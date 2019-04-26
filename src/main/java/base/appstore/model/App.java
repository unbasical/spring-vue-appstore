package base.appstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * App.
 * Model class persisted using JPA
 *
 * @author Gudrun Socher
 * @version 1.0
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class App {

    @Id
    @Column(name = "app_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private long views;

    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "app_id")
    private List<Comment> comments;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "app_id")
    private Set<Tag> tags;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "app_id")
    private List<Rating> ratings;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "app_id")
    private List<Screenshot> screenshots;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logo_id", referencedColumnName = "app_id")
    private Logo logo;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public long getAverageRating() {
        // TODO implement
        //Long sum = ratings.stream().reduce(0l,(a,b)->a+b);
        //return sum/ratings.size();
        return -1;
    }
}
