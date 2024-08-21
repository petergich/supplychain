package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RawMaterialPropotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The Propotion field cannot be null")
    private double propotion;

    @NotBlank(message = "The raw material cannot be null")
    @ManyToOne
    @JoinColumn(name = "raw_material")
    private RawMaterial rawMaterial;
    @NotBlank(message =  " the Final product must be specified")
    @ManyToOne
    @JoinColumn
    private Product product;


    public void setPropotion(@NotBlank(message = "The Propotion field cannot be null") double propotion) {
        this.propotion = propotion;
    }

    public void setProduct(@NotBlank(message = " the Fianal product must be specified") Product product) {
        this.product = product;
    }

    public void setRawMaterial(@NotBlank(message = "The raw material cannot be null") RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public Long getId() {
        return id;
    }

    @NotBlank(message = "The Propotion field cannot be null")
    public double getPropotion() {
        return propotion;
    }

    public @NotBlank(message = "The raw material cannot be null") RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public @NotBlank(message = " the Fianal product must be specified") Product getProduct() {
        return product;
    }
}
