package supplyChain.supplychain.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String Name;
    @NotBlank
    private String phone;

    public @NotBlank String getName() {
        return Name;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setName(@NotBlank String name) {
        Name = name;
    }

    public void setPhone_no(@NotBlank String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }
}
