package supplyChain.supplychain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemOrder {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "customerOrder",nullable = false)
    private CustomerOrder customerOrder;
    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;
    private Integer quantity;
}
