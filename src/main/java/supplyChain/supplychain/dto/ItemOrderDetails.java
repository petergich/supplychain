package supplyChain.supplychain.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter @Setter
public class ItemOrderDetails {
    private Long productId;
    private Long customerOrderId;
    private Integer quantity;
}
