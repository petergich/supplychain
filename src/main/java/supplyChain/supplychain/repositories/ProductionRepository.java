package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.Production;

import java.util.List;


@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
    List<Production> findByProduct(Product product);
}
