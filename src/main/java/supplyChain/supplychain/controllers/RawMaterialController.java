package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.dto.StockUpdate;
import supplyChain.supplychain.entities.RawMaterial;

import supplyChain.supplychain.services.RawMaterialService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rawmaterials")
public class RawMaterialController {
    @Autowired
    RawMaterialService rawMaterialService;



    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody RawMaterial rawMaterial){
        System.out.println("Creating Product");
        try{
            Object response = rawMaterialService.createRawMaterial(rawMaterial);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Unable To process", HttpStatus.OK);
        }
    }
    @GetMapping("/all")
    public Object ResponseEntity(){
        try{
            Object response = rawMaterialService.getAllRawMaterials();
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Fetch Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Object response = rawMaterialService.deleteRawMaterial(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(RawMaterial rawMaterial){
        try{
            Object response = rawMaterialService.editRawMaterial(rawMaterial);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Edit Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @PostMapping("/updatestock")
    public ResponseEntity<?> updateStock(@RequestBody StockUpdate stockUpdate){
        Long id = stockUpdate.getId();
        Integer quantity = stockUpdate.getQuantity();
        try{
            Object response = rawMaterialService.updateStock(id,quantity);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Update Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
}
