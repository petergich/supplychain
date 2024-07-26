package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.RawMaterialOrder;
import supplyChain.supplychain.repositories.RawMaterialOrderRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class RawMaterialOrderService {

    @Autowired
    RawMaterialOrderRepository rawMaterialOrderRepository;


    public Object createRawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        if (rawMaterialOrderRepository.existsByRawMaterial(rawMaterialOrder.getrawMaterial()) && rawMaterialOrderRepository.existsByPurchaseOrder(rawMaterialOrder.getPurchaseOrder())) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The Raw Material you are trying to add already exists in the purchase order");
            return body;
        } else {
            rawMaterialOrderRepository.save(rawMaterialOrder);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully added");
            return body;
        }

    }

    public Object updateRawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        if (rawMaterialOrderRepository.existsByRawMaterial(rawMaterialOrder.getrawMaterial()) && rawMaterialOrderRepository.existsByPurchaseOrder(rawMaterialOrder.getPurchaseOrder())) {
            RawMaterialOrder rawMaterialOrder_entity = rawMaterialOrderRepository.findByRawMaterialAndPurchaseOrder(rawMaterialOrder.getrawMaterial(), rawMaterialOrder.getPurchaseOrder());
            if (rawMaterialOrder.getPrice() != null && rawMaterialOrder.getPrice() != 0) {
                rawMaterialOrder_entity.setPrice(rawMaterialOrder.getPrice());
            }
            if (rawMaterialOrder.getQuantity() != null && rawMaterialOrder.getPrice() != 0) {
                rawMaterialOrder_entity.setQuantity(rawMaterialOrder.getQuantity());
            }
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully updated");
            return body;
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "This Raw Material does not exist in the the given purchase order");
            return body;
        }
    }
    public Object deleteRawMaterial(Long id){
        if(rawMaterialOrderRepository.existsById(id)){
            rawMaterialOrderRepository.delete(rawMaterialOrderRepository.findById(id).get());
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successful");
            return body;
        }else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Raw material order not found");
            return body;
        }
    }
}
