package supplyChain.supplychain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.entities.Customer;
import supplyChain.supplychain.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
        try{
            return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
        try{
            return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers(){
        try{
            return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
