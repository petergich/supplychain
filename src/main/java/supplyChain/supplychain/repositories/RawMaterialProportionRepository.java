package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.entities.RawMaterialPropotion;

import java.util.List;

@Repository
public interface RawMaterialProportionRepository extends JpaRepository<RawMaterialPropotion, Long> {
    boolean existsByProduct(Product product);
    boolean existsByRawMaterial(RawMaterial rawMaterial);
    List<RawMaterialPropotion> findByProduct(Product product);
    List<RawMaterialPropotion> findByRawMaterial(RawMaterial rawMaterial);
}
