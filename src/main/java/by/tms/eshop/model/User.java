package by.tms.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class User extends BaseEntity {

    private String password;
    private String login;
    private String name;
    private String surname;
    private String email;
    private LocalDate birthday;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Cart> carts;
}