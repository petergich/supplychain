package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.Supplier;
import supplyChain.supplychain.repositories.SupplierRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Object createSupplier(Supplier supplier){
        if(supplierRepository.existsByPhone(supplier.getPhone())){
            Map<String, Object> body = new HashMap<>();
            body.put("message", "A supplier with the phone number already exists");
            return body;
        }
        supplierRepository.save(supplier);
        Map<String, Object> body = new HashMap<>();
        body.put("message","Created successfully");
        return body;
    }
    public Object deleteSupplier(Long id){
        Map<String, Object> body = new HashMap<>();
        if(supplierRepository.findById(id).isPresent()){
          supplierRepository.delete(supplierRepository.findById(id).get());
          body.put("message","Deleted successfully");
          return body;
        }
        else{
            body.put("message","A supplier with the Id could not be found");
            return body;
        }
    }
}
