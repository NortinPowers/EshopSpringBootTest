package by.tms.eshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column
    private String password;
    @Column
    private String login;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private LocalDate birthday;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<Cart> carts;
}