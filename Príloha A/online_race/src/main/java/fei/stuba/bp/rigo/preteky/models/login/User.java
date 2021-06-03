package fei.stuba.bp.rigo.preteky.models.login;

import fei.stuba.bp.rigo.preteky.models.sql.Club;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @NotEmpty
    @Size(min = 2)
    @Column(name = "username", nullable = false)
    private String username;
    @NotEmpty
    @Email
    @Size(min = 2)
    @Column(name = "email", nullable = false)
    private String email;
    @NotEmpty
    @Size(min = 5)
    @Column(name = "password", nullable = false)
    private String password;
    @NotEmpty
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "enabled")
    private Boolean enabled;


    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "club_id",referencedColumnName = "id")
    private Club club;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    public boolean isEnabled() {
        return enabled;
    }
}
