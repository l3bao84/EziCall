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

import java.time.LocalDate;
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

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "phonenumber", nullable = false)
    private String phonenumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserActivityLog> userActivityLogs;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ticket> ticketsAssigned;

    public User() {
    }

    public User(String userId, String firstname, String lastname, String email, String username, String phonenumber, String password, LocalDate birthDate, Gender gender, Role role, Set<UserActivityLog> userActivityLogs, Set<Ticket> ticketsAssigned) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.phonenumber = phonenumber;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.userActivityLogs = userActivityLogs;
        this.ticketsAssigned = ticketsAssigned;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                '}';
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
