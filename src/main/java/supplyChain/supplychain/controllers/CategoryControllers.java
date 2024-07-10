package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.security.JWTUtil;
import supplyChain.supplychain.services.ProductCategoryService;


import java.util.Map;
@RequestMapping
@RestController
public class CategoryControllers {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    JWTUtil jwt;


    @PostMapping("/createcategory")
    public ResponseEntity<?> createCategory(@RequestBody ProductCategory category, @RequestHeader Map<String, String> headers) {

        try {
            Object response = productCategoryService.createCategory(category);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }


    }
}