package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supplyChain.supplychain.entities.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
