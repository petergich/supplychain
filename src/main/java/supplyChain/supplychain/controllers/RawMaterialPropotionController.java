package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
