package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Production {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank ( message = "The production name cannot be null")
    private String name;
    @NotBlank( message = "The final Product Quantity Cannot be null")
    private Integer finalProductQuantity;
    @NotBlank ( message = "The product cannot be null")
    @ManyToOne
    @JoinColumn(name = "productName")
    Product product;
    @NotBlank
    LocalDate date;


    public @NotBlank LocalDate getDate() {
        return date;
    }

    public void setDate(@NotBlank LocalDate date) {
        this.date = date;
    }

    private boolean finished;

    public void setProduct(@NotBlank(message = "The product cannot be null") Product product) {
        this.product = product;
    }

    public @NotBlank(message = "The product cannot be null") Product getProduct() {
        return product;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setFinalProductQuantity(@NotNull(message = "The final Product Quantity Cannot be null") Integer finalProductQuantity) {
        this.finalProductQuantity = finalProductQuantity;
    }

    public void setName(@NotNull(message = "The production name cannot be null") String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank(message = "The production name cannot be null") String getName() {
        return name;
    }

    public @NotBlank(message = "The final Product Quantity Cannot be null") Integer getFinalProductQuantity() {
        return finalProductQuantity;
    }

    public boolean isFinished() {
        return finished;
    }
}
