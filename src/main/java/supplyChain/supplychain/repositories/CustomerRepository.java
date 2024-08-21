package supplyChain.supplychain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supplyChain.supplychain.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPhone(String phone);
}
