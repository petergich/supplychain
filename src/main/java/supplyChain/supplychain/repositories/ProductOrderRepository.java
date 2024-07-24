package supplyChain.supplychain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductOrder;
import supplyChain.supplychain.entities.PurchaseOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    boolean existsByProduct(Product product);
    boolean existsByPurchaseOrder(PurchaseOrder purchaseOrder);

    @Query("SELECT p FROM ProductOrder p WHERE p.product = :product AND p.purchaseOrder = :purchaseOrder")
    ProductOrder findByProductAndPurchaseOrder(@Param("product") Product product, @Param("purchaseOrder") PurchaseOrder purchaseOrder);
}
