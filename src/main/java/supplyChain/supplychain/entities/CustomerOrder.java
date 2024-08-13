package supplyChain.supplychain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "customer",nullable = false)
    private Customer customer;
    private LocalDate date;
    private boolean delivered;
}
