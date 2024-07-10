package supplyChain.supplychain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Category Name missing")
    public String name;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}