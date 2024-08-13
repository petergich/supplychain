package supplyChain.supplychain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.dto.ProductDetails;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.entities.RawMaterialPropotion;
import supplyChain.supplychain.repositories.ProductCategoryRepository;
import supplyChain.supplychain.repositories.ProductRepository;
import supplyChain.supplychain.repositories.ProductionRepository;
import supplyChain.supplychain.repositories.RawMaterialProportionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductService {
    @Autowired
    RawMaterialPropotionService rawMaterialPropotionService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    RawMaterialProportionRepository rawMaterialProportionRepository;
    @Autowired
    ProductionRepository productionRepository;



    public Object createProduct(ProductDetails productDetails){

        Product product = new Product();
        if( !productCategoryRepository.existsByName(productDetails.getCategory())){
            Map<String, Object> body = new HashMap<>();
            body.put("status", "Category not found");
            return body;
        }
        ProductCategory category = productCategoryRepository.findByName(productDetails.getCategory()).get();
        product.setCategory(category);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        if(productRepository.existsByName(product.getName())){
            Map<String, Object> body = new HashMap<>();
            body.put("status", "Another product with the same name exists");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            productRepository.save(product);
            body.put("products", product);
            body.put("status", "Successfully added");
            return body;
        }
    }
    public Object getAllProducts(){
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Successful");
        body.put("products",productRepository.findAll());
        return body;
    }
    public Object  editProduct(Product product){
        if(productRepository.existsById(product.getId())){
            Product product_instance = productRepository.findById(product.getId()).get();
            product_instance.setName(product.getName().isEmpty()?product_instance.getName():product.getName());

            product_instance.setPrice(product.getPrice()!=null&& product.getPrice()>0?product.getPrice():product_instance.getPrice());
            productRepository.save(product_instance);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Successfully Updated");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The product does not exist");
            return body;
        }
    }
    public Object deleteProduct(Long id){
        if(productRepository.existsById(id)){
            Product product = productRepository.findById(id).get();
            List<RawMaterialPropotion> rawMateriialProportions = rawMaterialProportionRepository.findByProduct(product);
//            for (RawMaterialPropotion rawMaterialPropotion : rawMateriialProportions){
//                rawMaterialPropotionService.deleteRawMaterialProportion(rawMaterialPropotion);
//            }
//            List<Production> productions = productionRepository.findByProduct(product);
//            for (Production production : productions ){
//                productionService.deleteProduction(production);
//            }
            productRepository.delete(product);
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Deleted successfully");
            return body;
        }
        else{
            Map<String, Object> body = new HashMap<>();
            body.put("message", "The product does not exist");
            return body;
        }
    }
    public Product getProductById(Long id) throws Exception{
        return productRepository.findById(id).orElseThrow(() ->new Exception("The Product could not be found"));
    }
    public String updateStock(Long id, Integer quantity){


        Product existingPRODUCT= productRepository.findById(id).orElseThrow(()->new RuntimeException("product does not exist"));
        Integer finalQuatity = existingPRODUCT.getQuantity()+quantity;
        if (finalQuatity>=0){
            existingPRODUCT.setQuantity(finalQuatity);
            productRepository.save(existingPRODUCT);
            return "SUCCESSFULY UPDATED";
        }
        return "quantity is not available";


    }
}
