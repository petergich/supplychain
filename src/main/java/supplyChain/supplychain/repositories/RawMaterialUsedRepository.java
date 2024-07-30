package supplyChain.supplychain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.Production;
import supplyChain.supplychain.entities.RawMaterialUsed;

import java.util.List;

@Repository
public interface RawMaterialUsedRepository extends JpaRepository<RawMaterialUsed, Long> {
    List<RawMaterialUsed> findByProduction(Production production);
}
