package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.services.ProductCategoryService;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
    List<Product> findByCategory(ProductCategory productCategory);
}
