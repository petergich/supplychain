package supplyChain.supplychain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.entities.RawMaterialOrder;
import supplyChain.supplychain.entities.PurchaseOrder;

import java.util.List;

@Repository
public interface RawMaterialOrderRepository extends JpaRepository<RawMaterialOrder, Long> {
    boolean existsByRawMaterial(RawMaterial rawMaterial);
    boolean existsByPurchaseOrder(PurchaseOrder purchaseOrder);
    List<RawMaterialOrder> findByPurchaseOrder(PurchaseOrder purchaseOrder);

    @Query("SELECT r FROM RawMaterialOrder r WHERE r.rawMaterial = :rawMaterial AND r.purchaseOrder = :purchaseOrder")
    RawMaterialOrder findByRawMaterialAndPurchaseOrder(@Param("rawMaterial") RawMaterial rawMaterial, @Param("purchaseOrder") PurchaseOrder purchaseOrder);
}
