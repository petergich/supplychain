package supplyChain.supplychain.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.entities.ProductOrder;
import supplyChain.supplychain.services.ProductOrderServise;

@RestController
@RequestMapping("/productorder")
public class ProductOrderController {
    @Autowired
    ProductOrderServise productOrderServise;
    @PostMapping("/create")
    public ResponseEntity<?> addProductOrder(ProductOrder productOrder){
        try{
            Object response = productOrderServise.createProductOrder(productOrder);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateProductOrder(ProductOrder productOrder){
        try{
            Object response = productOrderServise.updateProduct(productOrder);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductOrder(@PathVariable Long id){
        try{
            Object response = productOrderServise.deleteProductOrder(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
