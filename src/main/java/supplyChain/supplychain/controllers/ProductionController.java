package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.dto.ProductionDetails;
import supplyChain.supplychain.services.ProductionService;

@RestController
@RequestMapping("/production")
public class ProductionController {
    @Autowired
    ProductionService productionService;


    @PostMapping("/create")
    public ResponseEntity<?> createProduction(@RequestBody ProductionDetails productionDetails){
        if(productionDetails.getProductId() != null && productionDetails.getQuantity() != null && productionDetails.getStatus()) {
            Object response = productionService.createProduction(productionDetails.getProductId(), productionDetails.getQuantity(), productionDetails.getStatus());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Please ensure that all the required data if filled", HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @PostMapping
    public ResponseEntity<?> completeProduction(@RequestBody Long productionId){
        Object response = productionService.completeProduction(productionId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllProductions(){
       Object response = productionService.getAllProductions();
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
