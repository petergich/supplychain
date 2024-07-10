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
    ProductRepository productRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @PostMapping
    public Object createCategory(ProductCategory category){

            Optional<ProductCategory> productcategory = productCategoryRepository.findByName(category.getName());
            if (productCategoryRepository.existsByName(category.getName())) {
                throw new IllegalArgumentException("The Category already exists");
            }
            productCategoryRepository.save(category);
            Map<String, Object> body = new HashMap<>();
            List<ProductCategory> categories = productCategoryRepository.findAll();
            List<Product> products = productRepository.findAll();
            body.put("categories", categories);
            body.put("products", products);
            body.put("status", "Created Successfully");
            return body;

    }
}
