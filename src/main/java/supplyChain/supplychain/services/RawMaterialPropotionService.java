package supplyChain.supplychain.services;


import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.dto.RawmaterialProportionDetails;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.entities.RawMaterialPropotion;
import supplyChain.supplychain.repositories.ProductRepository;
import supplyChain.supplychain.repositories.RawMaterialProportionRepository;
import supplyChain.supplychain.repositories.RawMaterialRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class RawMaterialPropotionService {
    @Autowired
    RawMaterialProportionRepository rawMaterialProportionRepository;
    @Autowired
    RawMaterialRepository rawMaterialRepository;
    @Autowired
    ProductRepository productRepository;

    public Object createRawMaterialPropotion(RawmaterialProportionDetails rawmaterialProportionDetails){
        if(! productRepository.existsById(rawmaterialProportionDetails.getProductId())){
            Map<String, String> object = new HashMap<>();
            object.put("message","The Selected product does not exist");
            return object;
        }
        if(! rawMaterialRepository.existsById(rawmaterialProportionDetails.getRawMaterialId())){
            Map<String, String> object = new HashMap<>();
            object.put("message","The Selected Raw Material does not exist");
            return object;
        }
        if(rawMaterialProportionRepository.existsByRawMaterial( rawMaterialRepository.findById(rawmaterialProportionDetails.getRawMaterialId()).get()) && rawMaterialProportionRepository.existsByProduct(productRepository.findById(rawmaterialProportionDetails.getProductId()).get())){
            Map<String, String> object = new HashMap<>();
            object.put("message","The raw material already has a relation with the product. Please edit or delete the relation");
            return object;
        }
        else{
            RawMaterialPropotion rawMaterialPropotion = new RawMaterialPropotion();
            rawMaterialPropotion.setRawMaterial(rawMaterialRepository.findById(rawmaterialProportionDetails.getRawMaterialId()).get());
            rawMaterialPropotion.setProduct(productRepository.findById(rawmaterialProportionDetails.getProductId()).get());
            rawMaterialPropotion.setPropotion(rawmaterialProportionDetails.getPropotion());
            rawMaterialProportionRepository.save(rawMaterialPropotion);
            Map<String, String> object = new HashMap<>();
            object.put("message","Successful");
            return object;
        }
    }
//    public Object updateRawMaterialPropotion(RawMaterialPropotion rawMaterialPropotion){
//
//    }
    public Object getByProduct(Long id){
        List<RawMaterialPropotion> rawMaterialProportions = rawMaterialProportionRepository.findByProduct(productRepository.findById(id).orElseThrow(() -> new Error("not found")));
        return rawMaterialProportions;
    }
    public String deleteRawMaterialProportion(Long id) throws Exception{
        RawMaterialPropotion rawMaterialProportion = rawMaterialProportionRepository.findById(id).orElseThrow(()->new Exception("Not found"));
        rawMaterialProportionRepository.delete(rawMaterialProportion);
        return "Deleted";
    }
    public List<RawMaterialPropotion> findByRawMaterial(RawMaterial rawMaterial){
        List<RawMaterialPropotion> rawMaterialPropotions = rawMaterialProportionRepository.findByRawMaterial(rawMaterial);
        return rawMaterialPropotions;

    }
}
