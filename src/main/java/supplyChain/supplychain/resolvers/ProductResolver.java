package supplyChain.supplychain.resolvers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.repositories.ProductRepository;

import java.util.List;


@Controller
public class ProductResolver {
    @Autowired
    private ProductRepository productRepository;

    @QueryMapping
    public List<Product> products(){
        return productRepository.findAll();
    }

    @QueryMapping()
    public Product product(@Argument Long id) {
        if (productRepository.existsById(id)) {
            return productRepository.findById(id).get();
        } else {
            return null;
        }
    }

}
