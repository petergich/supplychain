package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.security.JWTUtil;
import supplyChain.supplychain.services.ProductCategoryService;


import java.util.Map;
@RequestMapping("/categories")
@RestController
public class CategoryController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    JWTUtil jwt;


    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody ProductCategory category, @RequestHeader Map<String, String> headers) {

        try {
            Object response = productCategoryService.createCategory(category);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }


    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories(){
        try{
            Object response = productCategoryService.getAllProductCategories();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}