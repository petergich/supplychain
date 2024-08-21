package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.dto.ProductDetails;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.services.ProductService;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDetails product) {
        System.out.println("Creating Product");
        try {
            Object response = productService.createProduct(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Unable To process", HttpStatus.OK);
        }
    }

    @GetMapping("/all")
    public Object ResponseEntity() {
        try {
            Object response = productService.getAllProducts();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Fetch Operation failed");
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
       try{
            Object response = productService.deleteProduct(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(@RequestBody Product product) {
        try {
            Object response = productService.editProduct(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Edit Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Product could not be found", HttpStatus.NOT_FOUND);
        }
    }
}
