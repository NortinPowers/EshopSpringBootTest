package by.tms.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity{

//    @Id
////    @Column
//    @GeneratedValue(strategy = IDENTITY)
//    private Long id;
//    @Column
    private String name;
//    @Column
    private BigDecimal price;
    //    @Enumerated(ORDINAL) - без таблицы и нет защиты при изменения enum
//    private ProductType type;
    @ManyToOne
    @JoinColumn(name = "product_category_id")
//    @JoinColumn(name = "product_category_id", referencedColumnName = "id")
    private ProductCategory productCategory;
//    @Column
    private String info;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Cart> cart;
}