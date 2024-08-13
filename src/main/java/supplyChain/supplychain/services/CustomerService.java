package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.Customer;
import supplyChain.supplychain.repositories.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;


    public Customer createCustomer(Customer customer) throws Exception{
        if(customerRepository.existsByPhone(customer.getPhone())){
            throw new Exception("A customer with the phone number already exists");
        }
        Customer createdCustomer = customerRepository.save(customer);
        return createdCustomer;
    }
    public Customer updateCustomer(Customer customer) throws Exception{
        Customer customer_instance = customerRepository.findById(customer.getId()).orElseThrow(()-> new Exception("The customer could not be found"));
        if(customer.getPhone() !=null ){
            customer_instance.setPhone(customer.getPhone());
        }
        if(customer.getName() !=null){
            customer_instance.setName(customer.getName());
        }
        customerRepository.save(customer_instance);
        return customer_instance;   
    }
    public Customer findById(Long id) throws Exception{
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new Exception("Customer not found"));
        return customer;
    }
}
