package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.dto.ItemOrderDetails;
import supplyChain.supplychain.services.ItemOrderService;

@RestController("/itemorder")
@RequestMapping
public class ItemOrderController {
    @Autowired
    ItemOrderService itemOrderService;
    @PostMapping("/create")
    public ResponseEntity<?> createItemOrder(@RequestBody ItemOrderDetails itemOrderDetails) {
        try{
            return new ResponseEntity<>(itemOrderService.createItemOrder(itemOrderDetails), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findbycustomerorder/{id}")
    public ResponseEntity<?> findbycustomerorder(@PathVariable Long customerOrderId) {
        try{
            return new ResponseEntity<>(itemOrderService.findItemOrdersByCustomerOrder(customerOrderId),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
