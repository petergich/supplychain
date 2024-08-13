package supplyChain.supplychain.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class ProductionDetails {
    private LocalDate date;
    private Long productId;
    private Integer quantity;
    private boolean status;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity( Integer quantity) {
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
