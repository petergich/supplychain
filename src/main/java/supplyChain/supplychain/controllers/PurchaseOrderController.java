package supplyChain.supplychain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.dto.PurchaseOrderDetails;
import supplyChain.supplychain.entities.PurchaseOrder;
import supplyChain.supplychain.services.PurchaseOrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;


    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrderDetails purchaseOrderDetails){
        try{
            Object response = purchaseOrderService.createPurchaseOrder(purchaseOrderDetails);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update")
    public Object updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        try{
            Object response = purchaseOrderService.updatePurchaseOrder(purchaseOrder);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable Long id){
        try{
            Object response = purchaseOrderService.deletePurchaseOrder(id);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllPurchaseOrders(){
        List<PurchaseOrder> response = purchaseOrderService.getAllPurchaseOrders();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPurchaseOrderById(@PathVariable Long id){
        try {
            PurchaseOrder response = purchaseOrderService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Purchase Order not found");
            return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
        }
    }
}
