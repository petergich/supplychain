package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    private Integer price;
    private Integer quantity = 0;
    @ManyToOne
    @JoinColumn(name = "category_name", nullable = false)
    private ProductCategory category;

    public Product() {

    }

    public Product(Long id, String name, Integer price, Integer quantity, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setPrice(Integer price){
        this.price = price;
    }
    public Integer getPrice(){
        return price;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public void setCategory(ProductCategory category){
        this.category = category;
    }
    public ProductCategory getCategory(){
        return category;
    }
}
