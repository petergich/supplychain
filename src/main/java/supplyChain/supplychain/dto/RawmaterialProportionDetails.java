package supplyChain.supplychain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RawmaterialProportionDetails {

    private double propotion;
    @NotNull
    private Long rawMaterialId;
    @NotNull

    private Long productId;

    public RawmaterialProportionDetails(double propotion, Long rawmaterialid, Long productid) {
        this.propotion = propotion;
        this.rawMaterialId = rawmaterialid;
        this.productId = productid;
    }

    public RawmaterialProportionDetails() {
    }

    public double getPropotion() {
        return propotion;
    }

    public Long getRawMaterialId() {
        return rawMaterialId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setPropotion(double propotion) {
        this.propotion = propotion;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
