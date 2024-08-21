package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supplyChain.supplychain.entities.ProductCategory;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findByName(String name);
    boolean existsByName(String name);
}
