package supplyChain.supplychain.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.entities.PurchaseOrder;
import supplyChain.supplychain.services.PurchaseOrderService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/purchaseorder")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;


    @PostMapping("/create")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        try{
            Object response = purchaseOrderService.createPurchaseOrder((purchaseOrder));
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            Map<String, String> body = new HashMap<>();
            body.put("message", "An error occurred while creating the Purchase Order");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @PostMapping("/update")
    public Object updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        try{
            Object response = purchaseOrderService.updatePurchaseOrder(purchaseOrder);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            Map<String, String> body = new HashMap<>();
            body.put("message", "An error occurred while updating the Purchase Order");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable Long id){
        try{
            Object response = purchaseOrderService.deletePurchaseOrder(id);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            Map<String, String> body = new HashMap<>();
            body.put("message", "An error occurred while creating the Purchase Order");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
}
