package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.Supplier;
import supplyChain.supplychain.repositories.SupplierRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier)throws Exception{
        if(supplierRepository.existsByPhone(supplier.getPhone())){
            throw new Exception("A Supplier with the same phone already exists");
        }
        Supplier savedSupplier = supplierRepository.save(supplier);
        return savedSupplier;
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
    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }
}
