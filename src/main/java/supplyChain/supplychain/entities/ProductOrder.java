package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class ProductOrder {
   @Id
   @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;


   @ManyToOne
   @JoinColumn(name = "product",nullable = false)
   private Product product;

   @ManyToOne
   @JoinColumn(name = "purchaseOrder", nullable = false)
   private PurchaseOrder purchaseOrder;

   @NotNull
   private Integer quantity;
   @NotNull
   private Integer price;

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {

        this.purchaseOrder = purchaseOrder;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(@NotNull Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(@NotNull Integer price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public @NotNull Integer getQuantity() {
        return quantity;
    }

    public @NotNull Integer getPrice() {
        return price;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public Product getProduct() {
        return product;
    }
}
