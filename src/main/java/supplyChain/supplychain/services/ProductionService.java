package supplyChain.supplychain.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.Production;
import supplyChain.supplychain.entities.RawMaterialPropotion;
import supplyChain.supplychain.entities.RawMaterialUsed;
import supplyChain.supplychain.repositories.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductionService {
    @Autowired
    ProductionRepository productionRepository;
    @Autowired
    RawMaterialUsedRepository rawMaterialUsedRepository;
    @Autowired
    RawMaterialRepository rawMaterialRepository;
    @Autowired
    ProductService productService;
    @Autowired
    RawMaterialProportionRepository rawMaterialProportionRepository;
    @Autowired
    ProductRepository productRepository;


    public Object createProduction(Long productId, Integer quantity,boolean status){
        Product product = productRepository.findById(productId).orElseThrow(()->new RuntimeException("the product does not exist"));
        if(rawMaterialProportionRepository.existsByProduct(productRepository.findById(productId).get())){
           List<RawMaterialPropotion> rawMatrerialPropotions = rawMaterialProportionRepository.findByProduct(product);
           for(RawMaterialPropotion rawMaterialPropotion: rawMatrerialPropotions){
               if(rawMaterialPropotion.getPropotion() * quantity > rawMaterialPropotion.getRawMaterial().getQuantity()){
                   Map<String, String> object = new HashMap<>();
                   object.put("message", "The quantity for raw material" + rawMaterialPropotion.getRawMaterial().getName()+ "is less than the amount required to produce " + quantity + " of " + product.getName());
                   return object;
               }
           }
            Production production = new Production();
            production.setName("production" +production.getId() + "of " + quantity + " for " + product.getName());
            production.setFinalProductQuantity(quantity);
            production.setFinished(status);
            productionRepository.save(production);
            for(RawMaterialPropotion rawMaterialPropotion: rawMatrerialPropotions){
                RawMaterialUsed rawMaterialUsed = new RawMaterialUsed();
                rawMaterialUsed.setQuantity(quantity * rawMaterialPropotion.getPropotion());
                rawMaterialUsed.setRawMaterial(rawMaterialPropotion.getRawMaterial());
                rawMaterialUsed.setProduction(production);
                rawMaterialUsedRepository.save(rawMaterialUsed);
                rawMaterialPropotion.getRawMaterial().setQuantity(rawMaterialPropotion.getRawMaterial().getQuantity()-rawMaterialPropotion.getPropotion() * quantity);
                rawMaterialRepository.save(rawMaterialPropotion.getRawMaterial());
            }

            if(status){
                production.setFinished(true);
                product.setQuantity(product.getQuantity() + quantity);
                productRepository.save(product);
            }
            Map<String, String> object = new HashMap<>();
            object.put("message","The production has been successfully saved");
            return object;

        } else {
            Map<String, String> object = new HashMap<>();
            object.put("message","This final product does not have any raw materials connected. Please define the relations first to continue with production process");
            return object;
        }
    }
    public Object completeProduction(Long productionId){
        Production production = productionRepository.findById(productionId).orElseThrow(()->new RuntimeException("The Production instance specified cannot be found"));
        if(production.isFinished()){
            Map<String, String> object = new HashMap<>();
            object.put("message","The production instance has already ben completed");
            return object;
        }else {
            production.setFinished(true);
            production.getProduct().setQuantity(production.getProduct().getQuantity() + production.getFinalProductQuantity());
            productRepository.save(production.getProduct());
            Map<String, String> object = new HashMap<>();
            object.put("message","The production has been successfully completed");
            return object;
        }

    }
    public Object getAllProductions(){
        List<Production> productions = productionRepository.findAll();
        Map<Production, List<RawMaterialUsed>> production_instances = new HashMap<>();
        for(Production production : productions){
            List<RawMaterialUsed> rawMaterialUsed = rawMaterialUsedRepository.findByProduction(production);
            production_instances.put(production, rawMaterialUsed);
        }
        return production_instances;
    }
}
