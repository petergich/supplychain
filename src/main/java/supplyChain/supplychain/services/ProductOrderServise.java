package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductOrder;
import supplyChain.supplychain.repositories.ProductOrderRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductOrderServise {

    @Autowired
    ProductOrderRepository productOrderRepository;


    public Object createProductOrder(ProductOrder productOrder) {
        if (productOrderRepository.existsByProduct(productOrder.getProduct()) && productOrderRepository.existsByPurchaseOrder(productOrder.getPurchaseOrder())) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The product you are trying to add already exists in the purchase order");
            return body;
        } else {
            productOrderRepository.save(productOrder);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully added");
            return body;
        }

    }

    public Object updateProduct(ProductOrder productOrder) {
        if (productOrderRepository.existsByProduct(productOrder.getProduct()) && productOrderRepository.existsByPurchaseOrder(productOrder.getPurchaseOrder())) {
            ProductOrder productOrder_entity = productOrderRepository.findByProductAndPurchaseOrder(productOrder.getProduct(), productOrder.getPurchaseOrder());
            if (productOrder.getPrice() != null && productOrder.getPrice() != 0) {
                productOrder_entity.setPrice(productOrder.getPrice());
            }
            if (productOrder.getQuantity() != null && productOrder.getPrice() != 0) {
                productOrder_entity.setQuantity(productOrder.getQuantity());
            }
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully updated");
            return body;
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "This product does not exist in the the given purchase order");
            return body;
        }
    }
    public Object deleteProductOrder(Long id){
        if(productOrderRepository.existsById(id)){
            productOrderRepository.delete(productOrderRepository.findById(id).get());
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfull");
            return body;
        }else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Product order not found");
            return body;
        }
    }
}
