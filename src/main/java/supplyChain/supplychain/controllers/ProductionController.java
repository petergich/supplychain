package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supplyChain.supplychain.entities.Production;
import supplyChain.supplychain.services.ProductionService;

@RestController
@RequestMapping("/production")
public class ProductionController {
    @Autowired
    ProductionService productionService;


    @PostMapping("/create")
    public ResponseEntity<?> createProduction(@RequestBody Long productId, Integer quantity,boolean status){
        Object response = productionService.createProduction(productId, quantity,status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> completeProduction(@RequestBody Long productionId){
        Object response = productionService.completeProduction(productionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
