package ATP.Project.EziCall.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(generator = "customerGenerator")
    @GenericGenerator(name = "customerGenerator", strategy = "ATP.Project.EziCall.idgenerator.CustomerIdGenerator")
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "full_name", nullable = false)
    private String fullname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    public Customer() {
    }

    public Customer(String customerId, String fullname, String email, String phoneNumber, String address, Gender gender, Set<Ticket> tickets) {
        this.customerId = customerId;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.tickets = tickets;
    }
}
