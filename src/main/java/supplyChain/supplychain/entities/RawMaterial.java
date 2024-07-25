package supplyChain.supplychain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class RawMaterial {
    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "The name of the Raw material is requred")
    private String name;
    @NotBlank(message = "The Price of the Raw Material is Required")
    private  Integer price;
    private double quantity= 0;


    public Long getId() {
        return id;
    }

    public @NotBlank(message = "The name of the Raw material is requred") String getName() {
        return name;
    }

    public @NotBlank(message = "The Price of the Raw Material is Required") Integer getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setName(@NotBlank(message = "The name of the Raw material is requred") String name) {
        this.name = name;
    }

    public void setPrice(@NotBlank(message = "The Price of the Raw Material is Required") Integer price) {
        this.price = price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
