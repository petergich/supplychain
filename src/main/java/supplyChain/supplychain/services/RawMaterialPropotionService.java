package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.RawMaterialPropotion;
import supplyChain.supplychain.repositories.RawMaterialProportionRepository;

import java.util.HashMap;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class RawMaterialPropotionService {
    @Autowired
    RawMaterialProportionRepository rawMaterialProportionRepository;


    public Object createRawMaterialPropotion(RawMaterialPropotion rawMaterialPropotion){
        if(rawMaterialProportionRepository.existsByRawMaterial( rawMaterialPropotion.getRawMaterial()) && rawMaterialProportionRepository.existsByProduct(rawMaterialPropotion.getProduct()) ){
            Map<String, String> object = new HashMap<>();
            object.put("message","The raw material already has a relation with the product. Please edit or delete the relation");
            return object;
        }
        else{
            rawMaterialProportionRepository.save(rawMaterialPropotion);
            Map<String, String> object = new HashMap<>();
            object.put("message","Successful");
            return object;
        }
    }
//    public Object updateRawMaterialPropotion(RawMaterialPropotion rawMaterialPropotion){
//
//    }
}
