package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.dto.PurchaseOrderDetails;
import supplyChain.supplychain.entities.PurchaseOrder;
import supplyChain.supplychain.entities.RawMaterialOrder;
import supplyChain.supplychain.repositories.PurchaseOrderRepository;
import supplyChain.supplychain.repositories.SupplierRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderService {
    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    RawMaterialOrderService rawMaterialOrderService;
    @Autowired
    SupplierRepository supplierRepository;


    public PurchaseOrder createPurchaseOrder(PurchaseOrderDetails purchaseOrderDetails) throws Exception{
        if(purchaseOrderRepository.existsByPoNumber(purchaseOrderDetails.getPoNumber())){
            throw new Exception("A purchase order with the same PO number already exists");
        }
        if(purchaseOrderDetails.getSupplierId()== null || purchaseOrderDetails.getDate()==null ||purchaseOrderDetails.getPoNumber()==null){
            throw new Exception("Please ensure all the fields are filled");
        }
        else{
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            if(!supplierRepository.existsById(purchaseOrderDetails.getSupplierId())){
                throw new Exception("The selected supplier does not exist");

            }else{
                purchaseOrder.setSupplier(supplierRepository.findById(purchaseOrderDetails.getSupplierId()).get());
                purchaseOrder.setDate(purchaseOrderDetails.getDate());
                purchaseOrder.setDelivered(purchaseOrderDetails.isDelivered());
                purchaseOrder.setPoNumber(purchaseOrderDetails.getPoNumber());
                purchaseOrderRepository.save(purchaseOrder);
                return purchaseOrder;
            }

        }
    }
    public Object deletePurchaseOrder(Long id) throws Exception{
        if(purchaseOrderRepository.existsById(id)){
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).get();
            List<RawMaterialOrder>  rawMaterialOrders = rawMaterialOrderService.findByPurchaseOrder(purchaseOrder.getId());
            for( RawMaterialOrder rawMaterialOrder : rawMaterialOrders){
                rawMaterialOrderService.deleteRawMaterialOrder(rawMaterialOrder.getId());
            }
            purchaseOrderRepository.delete(purchaseOrder);
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
    public PurchaseOrder updatePurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
        if(purchaseOrderRepository.existsById(purchaseOrder.getId())){
            PurchaseOrder purchaseOrder_instanse = purchaseOrderRepository.findById(purchaseOrder.getId()).get();
            if(purchaseOrder.getPoNumber()!= null && !purchaseOrderRepository.existsById(purchaseOrder.getId())){
                purchaseOrder_instanse.setPoNumber(purchaseOrder.getPoNumber());
            }

            if(purchaseOrder.getDate() != null){
                purchaseOrder_instanse.setDate((purchaseOrder.getDate()));
            }
            if(purchaseOrder.isDelivered() && !purchaseOrder_instanse.isDelivered()){
                purchaseOrder_instanse.setDelivered(true);
                purchaseOrderRepository.save(purchaseOrder_instanse);
                List<RawMaterialOrder> rawMaterialOrders=rawMaterialOrderService.findByPurchaseOrder(purchaseOrder_instanse.getId());
                for(RawMaterialOrder rawMaterialOrder: rawMaterialOrders){
                    rawMaterialOrderService.updateStock(rawMaterialOrder);
                }
            }
            if(!purchaseOrder.isDelivered() && purchaseOrder_instanse.isDelivered()){
                throw new Exception("A delivered Purchase order cannot be set back to not delivered");
            }

            return purchaseOrder_instanse;
        }
        else{
            throw new Exception("The Purchase Order was not found");
        }
    }
    public List<PurchaseOrder> getAllPurchaseOrders(){
         return purchaseOrderRepository.findAll();
    }
}
