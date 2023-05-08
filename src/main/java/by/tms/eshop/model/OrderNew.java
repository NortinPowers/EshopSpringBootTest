//package by.tms.eshop.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Entity
//@Table(name = "order_new")
//@Getter
//@Setter
//@NoArgsConstructor
//public class OrderNew {
//
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    private Long id;
//    @Column
//    private String name;
//    @Column
//    private LocalDate date;
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//    @OneToMany
//    private List<Product> product;
//}
