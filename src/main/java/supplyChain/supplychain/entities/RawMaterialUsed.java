package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RawMaterialUsed {

   @Id
   @GeneratedValue ( strategy = GenerationType.IDENTITY)
   private Long id;

    @NotBlank (message = "The Quantity Cannot be null")
   private double quantity;
    @NotBlank(message = "The Associated production instance cannot be null")
   @ManyToOne
   @JoinColumn(name = "production_name")
   private Production production;
    @NotBlank(message = "The Associated Raw Material cannot be null")
   @ManyToOne
   @JoinColumn(name = "raw_material_name")
   private RawMaterial rawMaterial;

    public void setRawMaterial(@NotBlank(message = "The Associated Raw Material cannot be null") RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public void setQuantity(@NotBlank(message = "The Quantity Cannot be null") Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setQuantity(@NotBlank(message = "The Quantity Cannot be null") double quantity) {
        this.quantity = quantity;
    }

    public @NotBlank(message = "The Quantity Cannot be null") double getQuantity() {
        return quantity;
    }

    public @NotBlank(message = "The Associated production instance cannot be null") Production getProduction() {
        return production;
    }

    public @NotBlank(message = "The Associated Raw Material cannot be null") RawMaterial getRawMaterial() {
        return rawMaterial;
    }
}
