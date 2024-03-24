package ATP.Project.EziCall.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "userGenerator")
    @GenericGenerator(name = "userGenerator", strategy = "ATP.Project.EziCall.idgenerator.UserIdGenerator")
    @Column(name = "user_id")
    private String userId;

    @Column(name = "full_name", nullable = false)
    private String fullname;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserActivityLog> userActivityLogs;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ticket> ticketsAssigned;

    public User() {
    }

    public User(String userId, String fullname, String username, String password, Role role, Set<UserActivityLog> userActivityLogs, Set<Ticket> ticketsAssigned) {
        this.userId = userId;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userActivityLogs = userActivityLogs;
        this.ticketsAssigned = ticketsAssigned;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
