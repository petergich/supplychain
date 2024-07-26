package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.Production;


@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
}
