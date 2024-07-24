package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotBlank(message="Username is required")
    private String username;
    @NotBlank(message="Password is required")
    private String password;
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @NotBlank(message="Username is required")
    private String email;
    private boolean emailVerified=false;
    @NotBlank(message="OTP is required")
    private String OTP;

    public void setOTP(String OTP){
        this.OTP = OTP;
    }
    public String getOTP(){
        return OTP;
    }
    public void setEmailVerified(boolean emailVerified){
        this.emailVerified = emailVerified;
    }
    public boolean getEmailVerified(){
        return emailVerified;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public UserRole getRole(){
        return userRole;
    }

    public void setRole(UserRole userRole){
        this.userRole =userRole;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
