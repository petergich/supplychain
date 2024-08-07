package supplyChain.supplychain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RawMaterialOrderDetails {
    private Long rawMaterialId;
    private Long purchaseOrderId;
    private Integer price;
    private Integer quantity;

}
