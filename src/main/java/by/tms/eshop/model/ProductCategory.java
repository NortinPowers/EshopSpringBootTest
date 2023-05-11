package by.tms.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "product_category")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ProductCategory extends BaseEntity {

    private String category;
    @OneToMany(mappedBy = "productCategory")
    @ToString.Exclude
    private List<Product> product;
}