package supplyChain.supplychain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
public class PurchaseOrderDetails {
    private String poNumber;
    private Long supplierId;
    private LocalDate date;
    private boolean delivered;

}
