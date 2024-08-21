package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.dto.RawmaterialProportionDetails;
import supplyChain.supplychain.services.RawMaterialPropotionService;

@RestController
@RequestMapping("/rawmaterialproportion")
public class RawMaterialPropotionController {

    @Autowired
    RawMaterialPropotionService rawMaterialPropotionService;


    @PostMapping("/create")
    public ResponseEntity<?> createRawMaterialProportion(@RequestBody RawmaterialProportionDetails rawmaterialProportionDetails){
        Object response = rawMaterialPropotionService.createRawMaterialPropotion(rawmaterialProportionDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getbyproduct/{id}")
    public ResponseEntity<?> getByProduct(@PathVariable Long id){
        Object response = rawMaterialPropotionService.getByProduct(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRawMaterialProportion(@PathVariable Long id){
        try{
            return new ResponseEntity(rawMaterialPropotionService.deleteRawMaterialProportion(id),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
