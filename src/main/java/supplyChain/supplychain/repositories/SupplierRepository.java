package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supplyChain.supplychain.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long > {
    boolean existsByPhone(String phone);
}
