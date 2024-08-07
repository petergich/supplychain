package supplyChain.supplychain.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import supplyChain.supplychain.dto.RawMaterialOrderDetails;
import supplyChain.supplychain.entities.RawMaterialOrder;
import supplyChain.supplychain.services.RawMaterialOrderService;

@RestController
@RequestMapping("/rawmaterialorder")
public class RawMaterialOrderController {
    @Autowired
    RawMaterialOrderService rawMaterialOrderService;
    @PostMapping("/create")
    public ResponseEntity<?> addProductOrder(RawMaterialOrderDetails rawMaterialOrderDetails){
        try{
            Object response = rawMaterialOrderService.createRawMaterialOrder(rawMaterialOrderDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateProductOrder(RawMaterialOrder rawMaterialOrder){
        try{
            Object response = rawMaterialOrderService.updateRawMaterialOrder(rawMaterialOrder);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductOrder(@PathVariable Long id) throws Exception{
        try{
            Object response = rawMaterialOrderService.deleteRawMaterialOrder(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HttpServerErrorException.InternalServerError e){
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
