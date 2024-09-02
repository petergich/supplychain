package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @ManyToOne
    @JoinColumn( name = "user")
    private User user;
}
