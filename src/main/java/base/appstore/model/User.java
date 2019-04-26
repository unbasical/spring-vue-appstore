package base.appstore.model;

import javax.persistence.*;
import java.util.List;

public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(
            mappedBy = "app_id",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<App> apps;
}
