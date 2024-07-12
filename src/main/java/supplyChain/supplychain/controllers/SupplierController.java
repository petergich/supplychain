package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.entities.Supplier;
import supplyChain.supplychain.services.SupplierService;
import supplyChain.supplychain.services.UserService;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    @Autowired
    SupplierService supplierService;


    @PostMapping("/createsupplier")
    public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier){
    try{
        Object response = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch(IllegalAccessError e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
    }
    @DeleteMapping("deletesupplier/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
        try{
            Object response = supplierService.deleteSupplier(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
