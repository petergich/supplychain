package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supplyChain.supplychain.entities.CustomerOrder;
import supplyChain.supplychain.entities.ItemOrder;
import supplyChain.supplychain.entities.Product;

import java.util.List;

public interface ItemOrderRepository extends JpaRepository<ItemOrder,Long> {
    List<ItemOrder> findByCustomerOrder(CustomerOrder customerOrder);
    List<ItemOrder> findByProduct(Product product);
}
