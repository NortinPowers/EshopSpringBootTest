package by.tms.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "carts")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Cart extends BaseEntity{

//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    private Long id;

//    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id")
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    //    @Column
    private boolean cart;
    //    @Column
    private boolean favorite;

    //    @Column
//    private int count;
    private Integer count;
}