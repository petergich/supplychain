package supplyChain.supplychain.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductionDetails {
    @NotBlank( message = "The productId cannot be null")
    private Long productId;
    @NotBlank(message = "The quantity field cannot be null")
    private Integer quantity;
    private boolean status;

    public void setProductId(@NotBlank(message = "The productId cannot be null") Long productId) {
        this.productId = productId;
    }

    public void setQuantity(@NotBlank(message = "The quantity field cannot be null") Integer quantity) {
        this.quantity = quantity;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public @NotBlank(message = "The quantity field cannot be null") Integer getQuantity() {
        return quantity;
    }

    public @NotBlank(message = "The productId cannot be null") Long getProductId() {
        return productId;
    }
}
