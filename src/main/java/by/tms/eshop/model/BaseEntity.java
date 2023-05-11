package by.tms.eshop.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@Data
public class BaseEntity {
    @Id
//    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
