package supplyChain.supplychain.dto;


import lombok.Data;

@Data
public class PasswordResetDetails {
    private String token;
    private String password;
}
