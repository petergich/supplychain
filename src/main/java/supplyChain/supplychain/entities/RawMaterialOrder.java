package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class RawMaterialOrder {
   @Id
   @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;


   @ManyToOne
   @JoinColumn(name = "rawMaterial",nullable = false)
   private RawMaterial rawMaterial;

   @ManyToOne
   @JoinColumn(name = "purchaseOrder", nullable = false)
   private PurchaseOrder purchaseOrder;

   @NotBlank
   private Integer quantity;
    @NotBlank
   private Integer price;

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {

        this.purchaseOrder = purchaseOrder;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public void setQuantity(@NotBlank Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(@NotBlank Integer price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public @NotBlank Integer getQuantity() {
        return quantity;
    }

    public @NotBlank Integer getPrice() {
        return price;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public RawMaterial getrawMaterial() {
        return rawMaterial;
    }
}
