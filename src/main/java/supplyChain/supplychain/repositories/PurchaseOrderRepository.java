package supplyChain.supplychain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.PurchaseOrder;
import supplyChain.supplychain.entities.Supplier;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    boolean existsByPoNumber(String poNumber);
    List<PurchaseOrder> findBySupplier(Supplier supplier);

}
