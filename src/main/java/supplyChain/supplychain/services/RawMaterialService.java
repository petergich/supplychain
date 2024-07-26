package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.repositories.RawMaterialRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class RawMaterialService {
    @Autowired
    RawMaterialRepository rawMaterialRepository;

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
        body.put("raw materials",rawMaterialRepository.findAll());
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
    public Object deleteRawMaterial(Long id){
        if(rawMaterialRepository.existsById(id)){
            rawMaterialRepository.delete(rawMaterialRepository.findById(id).get());
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Deleted successfully");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The Raw Material does not exist");
            return body;
        }
    }
    public String updateStock(Long id, Integer quantity){


        RawMaterial existingRawMaterial= rawMaterialRepository.findById(id).orElseThrow(()->new RuntimeException("Raw Material does not exist"));
        double finalQuatity = existingRawMaterial.getQuantity()+quantity;
        if (finalQuatity>=0){
            existingRawMaterial.setQuantity(finalQuatity);
            return "SUCCESSFULLY UPDATED";
        }
        return "quantity is not available";


    }
}
