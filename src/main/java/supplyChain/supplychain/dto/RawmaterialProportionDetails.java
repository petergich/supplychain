package supplyChain.supplychain.dto;

public class RawmaterialProportionDetails {

    private double propotion;
    private Long rawmaterialid;
    private Long productid;


    public double getPropotion() {
        return propotion;
    }

    public Long getRawMaterialId() {
        return rawmaterialid;
    }

    public Long getProductId() {
        return productid;
    }

    public void setPropotion(double propotion) {
        this.propotion = propotion;
    }

    public void setRawMaterialId(Long rawMaterialId) {
        this.rawmaterialid = rawMaterialId;
    }

    public void setProductId(Long productId) {
        this.productid = productId;
    }
}
