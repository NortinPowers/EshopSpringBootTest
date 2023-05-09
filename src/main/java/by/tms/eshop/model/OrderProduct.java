package by.tms.eshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "order_Products")
@NoArgsConstructor
@SuperBuilder
@Getter
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
//    private Long orderId;
//    private Long productId;
    @ManyToOne
    @JoinColumn(name = "order_Id", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}