package supplyChain.supplychain.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="Username is required")
    private String username;
    @NotBlank(message="Password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotBlank(message="Username is required")
    private String email;
    private boolean emailVerified=false;
    private boolean accountApproved = false;
    @NotBlank(message="OTP is required")
    private String OTP;

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public boolean isAccountApproved() {
        return accountApproved;
    }

    public void setAccountApproved(boolean accountApproved) {
        this.accountApproved = accountApproved;
    }

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

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public Long getId() {
        return id;
    }
}
