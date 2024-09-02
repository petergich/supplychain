package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.dto.ItemOrderDetails;
import supplyChain.supplychain.entities.CustomerOrder;
import supplyChain.supplychain.entities.ItemOrder;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.repositories.CustomerOrderRepository;
import supplyChain.supplychain.repositories.ItemOrderRepository;
import supplyChain.supplychain.repositories.ProductRepository;

import java.util.List;

@Service
public class ItemOrderService {
    @Autowired
    ItemOrderRepository itemOrderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CustomerOrderRepository customerOrderRepository;

    public ItemOrder createItemOrder(ItemOrderDetails itemOrderDetails) throws Exception {
        Product product = productRepository.findById(itemOrderDetails.getProductId()).orElseThrow(()->new Exception("Product NOT FOUND"));
        CustomerOrder customerOrder = customerOrderRepository.findById(itemOrderDetails.getCustomerOrderId()).orElseThrow(()-> new Exception("Customer order Not Found"));
        if(product.getQuantity()-itemOrderDetails.getQuantity()<0 && customerOrder.isDelivered()){
            throw new Exception("The quantity for "+product.getName()+"is not enough");
        }
        if(customerOrder.isDelivered()){
            product.setQuantity(product.getQuantity()+itemOrderDetails.getQuantity());
            productRepository.save(product);
        }
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setCustomerOrder(customerOrder);
        itemOrder.setCustomerOrder(customerOrder);
        itemOrder.setQuantity(itemOrder.getQuantity());
        itemOrderRepository.save(itemOrder);

        return itemOrder;
    }
    public List<ItemOrder> findItemOrdersByCustomerOrder(Long id) throws Exception{
        CustomerOrder customerOrder = customerOrderRepository.findById(id).orElseThrow(()->new Exception("Customer order Not Found"));
        return itemOrderRepository.findByCustomerOrder(customerOrder);
    }
    public List<ItemOrder> findByProduct(Product product){
        return itemOrderRepository.findByProduct(product);
    }
    public String deleteItemOrder(ItemOrder itemOrder){
        itemOrderRepository.delete(itemOrder);
        return "Item Order deleted successfully";
    }
}
