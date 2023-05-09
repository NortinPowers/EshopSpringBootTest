package by.tms.eshop.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
public class Cart {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

//    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column
    private boolean cart;
    @Column
    private boolean favorite;

    @Column
//    private int count;
    private Integer count;
}