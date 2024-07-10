package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supplyChain.supplychain.dto.ProductDetails;
import supplyChain.supplychain.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;



    @PostMapping("/createproduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDetails product){
        System.out.println("Creating Product");
        try{
            Object response = productService.createProduct(product);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Unable To process", HttpStatus.UNAUTHORIZED);
        }
    }
}
