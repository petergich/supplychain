package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.dto.ProductDetails;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.repositories.ProductCategoryRepository;
import supplyChain.supplychain.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public Object createProduct(ProductDetails productDetails){

        Product product = new Product();
        ProductCategory category;
//        try {
            category = productCategoryRepository.findByName(productDetails.getCategory()).get();
//        }catch (HttpClientErrorException.NotFound e) {
//            throw new IllegalArgumentException("The category does not exist");
//        }
        product.setCategory(category);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        if(productRepository.existsByName(product.getName())){
            Map<String, Object> body = new HashMap<>();
            List<ProductCategory> categories = productCategoryRepository.findAll();
            List<Product> products = productRepository.findAll();
            body.put("categories", categories);
            body.put("products", products);
            body.put("status", "Another product with the same name exists");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            productRepository.save(product);
            List<ProductCategory> categories = productCategoryRepository.findAll();
            List<Product> products = productRepository.findAll();
            body.put("categories", categories);
            body.put("products", products);
            body.put("status", "Successfully added");
            return body;
        }
    }
}
