package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import supplyChain.supplychain.dto.ProductDetails;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.repositories.ProductCategoryRepository;
import supplyChain.supplychain.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public Object createProduct(ProductDetails productDetails){

        Product product = new Product();
        if( !productCategoryRepository.existsByName(productDetails.getCategory())){
            Map<String, Object> body = new HashMap<>();
            body.put("status", "Category not found");
            return body;
        }
        ProductCategory category = productCategoryRepository.findByName(productDetails.getCategory()).get();
        product.setCategory(category);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        if(productRepository.existsByName(product.getName())){
            Map<String, Object> body = new HashMap<>();
            body.put("status", "Another product with the same name exists");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            productRepository.save(product);
            body.put("products", product);
            body.put("status", "Successfully added");
            return body;
        }
    }
    public Object getAllProducts(){
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Successful");
        body.put("products",productRepository.findAll());
        return body;
    }
    public Object  editProduct(Product product){
        if(productRepository.existsById(product.getId())){
            Product product_instance = productRepository.findById(product.getId()).get();
            product_instance.setName(product.getName().isEmpty()?product_instance.getName():product.getName());

            product_instance.setPrice(product.getPrice()!=null&& product.getPrice()>0?product.getPrice():product_instance.getPrice());
            productRepository.save(product_instance);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully Updated");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The product does not exist");
            return body;
        }
    }
    public Object deleteProduct(Long id){
        if(productRepository.existsById(id)){
            productRepository.delete(productRepository.findById(id).get());
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Deleted successfully");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The product does not exist");
            return body;
        }
    }
    public String updateStock(Long id, Integer quantity){


        Product existingPRODUCT= productRepository.findById(id).orElseThrow(()->new RuntimeException("product does not exist"));
        Integer finalQuatity = existingPRODUCT.getQuantity()+quantity;
        if (finalQuatity>=0){
            existingPRODUCT.setQuantity(finalQuatity);
            return "SUCCESSFULY UPDATED";
        }
        return "quantity is not available";


    }
}
