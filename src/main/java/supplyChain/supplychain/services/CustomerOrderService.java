package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.Customer;
import supplyChain.supplychain.entities.CustomerOrder;
import supplyChain.supplychain.entities.ItemOrder;
import supplyChain.supplychain.repositories.CustomerOrderRepository;
import supplyChain.supplychain.repositories.ItemOrderRepository;

import java.util.List;

@Service
public class CustomerOrderService {
    @Autowired
    CustomerOrderRepository customerOrderRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    ItemOrderService itemOrderService;
    @Autowired
    ProductService productService;
    public CustomerOrder createCustomerOrder(Long id,boolean delivered) throws Exception{
        Customer customer = customerService.findById(id);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(customer);
        customerOrder.setDelivered(delivered);
        customerOrderRepository.save(customerOrder);
        return  customerOrder;
    }

    public CustomerOrder findById(Long id) throws  Exception{
        CustomerOrder customerOrder = customerOrderRepository.findById(id).orElseThrow(()->new Exception("Not Found"));
        customerOrderRepository.save(customerOrder);
        return customerOrder;
    }
    public CustomerOrder deliverCustomerOrder(Long id)throws Exception{
        CustomerOrder customerOrder = findById(id);
        if(customerOrder.isDelivered()){
            throw new Exception("The Customer order is already set to delivered");
        }else{
            List<ItemOrder> itemOrders = itemOrderService.findItemOrdersByCustomerOrder(customerOrder);
            for(ItemOrder itemOrder: itemOrders){
                productService.updateStock(itemOrder.getProduct().getId(),itemOrder.getQuantity());
            }
            return customerOrder;
        }
    }


}
