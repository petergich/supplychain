package supplyChain.supplychain.users;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @NotNull(message="Role is required")
    private Set<UserRole> userRole;
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
    public Set<UserRole> getRole(){
        return userRole;
    }

    public void setRole(UserRole userRole){
        this.userRole = Collections.singleton(userRole);
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
