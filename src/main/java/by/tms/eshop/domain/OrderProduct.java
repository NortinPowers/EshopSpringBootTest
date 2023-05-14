//package by.tms.eshop.domain;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//
//@Entity
////@Table(name = "order_Products")
//@Table(name = "order_product")
//@NoArgsConstructor
//@SuperBuilder
//@Getter
//public class OrderProduct extends BaseEntity {
//
//    @ManyToOne
//    @JoinColumn(name = "order_Id")
//    private Order order;
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//}