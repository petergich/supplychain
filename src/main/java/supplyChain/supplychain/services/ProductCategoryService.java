package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.repositories.ProductCategoryRepository;
import supplyChain.supplychain.repositories.ProductRepository;
import supplyChain.supplychain.security.JWTUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductCategoryService {
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @PostMapping
    public Object createCategory(ProductCategory category){

            Optional<ProductCategory> productcategory = productCategoryRepository.findByName(category.getName());
            if (productCategoryRepository.existsByName(category.getName())) {
                Map<String, Object> body = new HashMap<>();
                body.put("message", "Product Category already exist");
                return body;
            }
            productCategoryRepository.save(category);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Created Successfully");
            return body;

    }
    public Object getAllProductCategories(){
        Map<String, Object> body = new HashMap<>();
        body.put("categories",productCategoryRepository.findAll());
        body.put("message", "successful");
        return body;
    }
    public Object deleteProductCategory(Long id){
        if(productCategoryRepository.existsById(id)){
            ProductCategory category = productCategoryRepository.findById(id).get();
            List<Product> products = productRepository.findByCategory(category);
            for(Product product : products){
                productService.deleteProduct(product.getId());
            }
            productCategoryRepository.delete(category);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "successful");
            return body;
        } else {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Product Category does not exist");
            return body;
        }
    }
}
