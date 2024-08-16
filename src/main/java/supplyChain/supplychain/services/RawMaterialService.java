package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.entities.RawMaterialPropotion;
import supplyChain.supplychain.entities.RawMaterialUsed;
import supplyChain.supplychain.repositories.RawMaterialRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RawMaterialService {
    @Autowired
    RawMaterialRepository rawMaterialRepository;
    @Autowired
    RawMaterialUsedService rawMaterialUsedService;
    @Autowired
    RawMaterialPropotionService rawMaterialPropotionService;
    public Object createRawMaterial(RawMaterial rawMaterial){

        if(rawMaterialRepository.existsByName(rawMaterial.getName())){
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Another raw material with the same name exists");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            RawMaterial savedRawMaterial = rawMaterialRepository.save(rawMaterial);
            body.put("raw material", savedRawMaterial);
            body.put("message", "Successfully added");
            return body;
        }
    }
    public Object getAllRawMaterials(){
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Successful");
        body.put("rawmaterials",rawMaterialRepository.findAll());
        return body;
    }
    public Object  editRawMaterial(RawMaterial rawMaterial){
        if(rawMaterialRepository.existsById(rawMaterial.getId())){
            RawMaterial rawMaterial_instance = rawMaterialRepository.findById(rawMaterial.getId()).get();
            rawMaterial_instance.setName(rawMaterial.getName().isEmpty()?rawMaterial_instance.getName():rawMaterial.getName());

            rawMaterial_instance.setPrice(rawMaterial.getPrice()!=null&& rawMaterial.getPrice()>0?rawMaterial.getPrice():rawMaterial_instance.getPrice());
            rawMaterialRepository.save(rawMaterial_instance);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully Updated");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The raw material does not exist");
            return body;
        }
    }
    public String deleteRawMaterial(Long id) throws Exception{
        if(rawMaterialRepository.existsById(id)){
            RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(()-> new Exception("Raw Material Not Found"));
            List<RawMaterialUsed> rawMaterialUseds = rawMaterialUsedService.findByRawMaterial(rawMaterial);
            for(RawMaterialUsed rawMaterialUsed : rawMaterialUseds){
                rawMaterialUsedService.deleteRawMaterialUsed(rawMaterialUsed.getId());
            }
            List<RawMaterialPropotion> rawMaterialPropotions = rawMaterialPropotionService.findByRawMaterial(rawMaterial);
            for(RawMaterialPropotion rawMaterialPropotion : rawMaterialPropotions){
                rawMaterialPropotionService.deleteRawMaterialProportion(rawMaterialPropotion.getId());
            }
            rawMaterialRepository.delete(rawMaterial);
            return "Deleted successfully";
        }
        else{

            return "The Raw Material does not exist";
        }
    }
    public Object updateStock(Long id, Integer quantity){


        RawMaterial existingRawMaterial= rawMaterialRepository.findById(id).orElseThrow(()->new RuntimeException("Raw Material does not exist"));
        double finalQuatity = existingRawMaterial.getQuantity()+quantity;
        if (finalQuatity>=0){
            existingRawMaterial.setQuantity(finalQuatity);
            rawMaterialRepository.save(existingRawMaterial);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully updated");
            return body;
        }
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Quantity not available");
        return body;


    }
    public RawMaterial getRawMaterialById(Long id) throws Exception{
        return rawMaterialRepository.findById(id).orElseThrow(()-> new Exception("The raw material does not exist"));
    }
}
