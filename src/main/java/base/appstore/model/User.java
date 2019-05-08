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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name;

    private String email;

    @CreatedDate
    @EqualsAndHashCode.Exclude
    private Date createDate;
    @LastModifiedDate
    @EqualsAndHashCode.Exclude
    private Date updateDate;

    @ElementCollection(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();

    /*@OneToMany(
            mappedBy = "user",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude*/
    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private List<App> apps = new ArrayList<>();

    public void setRoles(Set<Role> roles) {
        this.roles.retainAll(roles);
        this.roles.addAll(roles);
    }

    public void setApps(List<App> apps) {
        this.apps.retainAll(apps);
        this.apps.addAll(apps);
    }
}
