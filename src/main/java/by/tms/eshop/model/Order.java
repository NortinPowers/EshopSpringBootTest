package by.tms.eshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class Order {

    @Id
    private String id;
    @Column
    private LocalDate date;
//    private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;


}