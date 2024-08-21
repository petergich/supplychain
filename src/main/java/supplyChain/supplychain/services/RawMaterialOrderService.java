package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.dto.RawMaterialOrderDetails;
import supplyChain.supplychain.entities.PurchaseOrder;
import supplyChain.supplychain.entities.RawMaterial;
import supplyChain.supplychain.entities.RawMaterialOrder;
import supplyChain.supplychain.repositories.PurchaseOrderRepository;
import supplyChain.supplychain.repositories.RawMaterialOrderRepository;
import supplyChain.supplychain.repositories.RawMaterialRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RawMaterialOrderService {

    @Autowired
    RawMaterialOrderRepository rawMaterialOrderRepository;
    @Autowired
    RawMaterialRepository rawMaterialRepository;
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    RawMaterialService rawMaterialService;


    public RawMaterialOrder createRawMaterialOrder(RawMaterialOrderDetails rawMaterialOrderDetails) throws Exception{
        RawMaterial rawMaterial = rawMaterialService.getRawMaterialById(rawMaterialOrderDetails.getRawMaterialId());
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(rawMaterialOrderDetails.getPurchaseOrderId()).orElseThrow(()-> new Exception("The specified purchase order was not found"));
        if (rawMaterialOrderRepository.existsByRawMaterial(rawMaterial) && rawMaterialOrderRepository.existsByPurchaseOrder(purchaseOrderRepository.findById(rawMaterialOrderDetails.getPurchaseOrderId()).get())) {
           throw new Exception("The raw material is already added under this Purchase order");
        } else {
            RawMaterialOrder rawMaterialOrder = new RawMaterialOrder();
            rawMaterialOrder.setRawMaterial(rawMaterial);
            rawMaterialOrder.setPurchaseOrder(purchaseOrder);
            rawMaterialOrder.setPrice(rawMaterialOrderDetails.getPrice());
            rawMaterialOrder.setQuantity(rawMaterialOrderDetails.getQuantity());
            RawMaterialOrder rawMaterialOrder_created=rawMaterialOrderRepository.save(rawMaterialOrder);
            if(rawMaterialOrder.getPurchaseOrder().isDelivered()) {
                rawMaterialOrder.getrawMaterial().setQuantity(rawMaterialOrder.getrawMaterial().getQuantity() + rawMaterialOrder.getQuantity());
            }
            return rawMaterialOrder_created;
        }

    }

    public Object updateRawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        if (rawMaterialOrderRepository.existsByRawMaterial(rawMaterialOrder.getrawMaterial()) && rawMaterialOrderRepository.existsByPurchaseOrder(rawMaterialOrder.getPurchaseOrder())) {
            RawMaterialOrder rawMaterialOrder_entity = rawMaterialOrderRepository.findByRawMaterialAndPurchaseOrder(rawMaterialOrder.getrawMaterial(), rawMaterialOrder.getPurchaseOrder());
            if (rawMaterialOrder.getPrice() != null && rawMaterialOrder.getPrice() != 0) {
                rawMaterialOrder_entity.setPrice(rawMaterialOrder.getPrice());
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
    public String deleteRawMaterialOrder(Long id) throws Exception {
        if(rawMaterialOrderRepository.existsById(id)){
            rawMaterialOrderRepository.delete(rawMaterialOrderRepository.findById(id).get());
            return "Successful";
        }else{
            throw new Exception("Raw material was not found");
        }
    }
    public List<RawMaterialOrder> findByPurchaseOrder(Long id ) throws Exception {
        List<RawMaterialOrder> rawMaterialOrders = rawMaterialOrderRepository.findByPurchaseOrder(purchaseOrderRepository.findById(id).orElseThrow(()->new Exception("The Purchase order wwas not found")));
        return rawMaterialOrders;
    }
    public RawMaterial updateStock(RawMaterialOrder rawMaterialOrder) throws Exception {
        try {
            rawMaterialService.updateStock(rawMaterialOrder.getrawMaterial().getId(),rawMaterialOrder.getQuantity());
            rawMaterialOrderRepository.save(rawMaterialOrder);
            return rawMaterialOrder.getrawMaterial();
        } catch(HttpServerErrorException.InternalServerError e){
            throw new Exception("The transaction was not successful");
        }

    }

}
