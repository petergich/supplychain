package supplyChain.supplychain.dto;

public class StockUpdate {
    private Long id;
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
