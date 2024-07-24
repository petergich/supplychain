package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.PurchaseOrder;
import supplyChain.supplychain.repositories.PurchaseOrderRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class PurchaseOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;



    public Object createPurchaseOrder(PurchaseOrder purchaseOrder){
        if(purchaseOrderRepository.existsByPoNumber(purchaseOrder.getPoNumber())){
            Map<String, Object> body = new HashMap<>();
            body.put("message","A purchase order with the same purchase order number already exists");
            return body;
        }
        else{
            purchaseOrderRepository.save(purchaseOrder);
            Map<String, Object> body = new HashMap<>();
            body.put("message","Successfully added");
            return body;
        }
    }
    public Object deletePurchaseOrder(Long id){
        if(purchaseOrderRepository.existsById(id)){
            purchaseOrderRepository.delete(purchaseOrderRepository.findById(id).get());
            Map<String, Object> body = new HashMap<>();
            body.put("message","Successfully deleted");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message","Purchase Order does not exist");
            return body;
        }
    }
    public Object updatePurchaseOrder(PurchaseOrder purchaseOrder){
        if(purchaseOrderRepository.existsByPoNumber(purchaseOrder.getPoNumber())){
            PurchaseOrder purchaseOrder_instanse = purchaseOrderRepository.findById(purchaseOrder.getId()).get();
            if(purchaseOrder.getPoNumber()!= null && !purchaseOrder.getPoNumber().equals("")){
                purchaseOrder_instanse.setPoNumber(purchaseOrder.getPoNumber());
            }
            if(purchaseOrder.getSupplier()!= null && !purchaseOrder.getSupplier().equals("")){
                purchaseOrder_instanse.setSupplier(purchaseOrder.getSupplier());
            }
            if(purchaseOrder.getDate() != null){
                purchaseOrder_instanse.setDate((purchaseOrder.getDate()));
            }
            Map<String, Object> body = new HashMap<>();
            body.put("message","Successfully updated");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message","Update failed! Purchase order not found");
            return body;
        }
    }
}
