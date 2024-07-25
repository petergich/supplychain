package supplyChain.supplychain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.RawMaterialUsed;

@Repository
public interface RawMaterialUsedRepository extends JpaRepository<RawMaterialUsed, Long> {
}
