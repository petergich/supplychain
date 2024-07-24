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
    public ResponseEntity<?> createProduct(@RequestBody ProductDetails product){
        System.out.println("Creating Product");
        try{
            Object response = productService.createProduct(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Unable To process", HttpStatus.OK);
        }
    }
    @GetMapping("/all")
    public Object ResponseEntity(){
        try{
            Object response = productService.getAllProducts();
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Fetch Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        Object response = productService.deleteProduct(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PostMapping("/edit")
    public ResponseEntity<?> editProduct(Product product){
        try{
            Object response = productService.editProduct(product);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Edit Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id,@RequestBody Integer quantity){
        try{
            Object response = productService.updateStock(id,quantity);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Update Operation failed");
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
    }
}
