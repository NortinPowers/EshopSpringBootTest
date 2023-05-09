package by.tms.eshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@SuperBuilder
public class Cart {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long userId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;

    private Long productId;

//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private Product product;

    @Column
    private boolean cart;
    @Column
    private boolean favorite;
    @Column
    private Integer count;
}