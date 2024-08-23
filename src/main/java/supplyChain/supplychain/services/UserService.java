package supplyChain.supplychain.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.entities.User;
import supplyChain.supplychain.repositories.ProductCategoryRepository;
import supplyChain.supplychain.repositories.ProductRepository;
import supplyChain.supplychain.repositories.UserRepository;
import supplyChain.supplychain.entities.UserRole;
import supplyChain.supplychain.security.JWTAuthenticationResponse;
import supplyChain.supplychain.dto.LoginRequest;
import supplyChain.supplychain.security.Validation;
import supplyChain.supplychain.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.*;

@Service
public class UserService {
    @Autowired
    JWTAuthenticationResponse jwtAuthenticationResponse;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    private Validation validation;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Object login(LoginRequest loginRequest) {
        if(!userRepository.existsByUsername(loginRequest.getUsername())){
            Map<String, Object> body = new HashMap<>();
            body.put("message", "invalid credentials");
            return body;
        }
        User user = userRepository.findByUsername(loginRequest.getUsername()).get();
        if(!user.getEmailVerified()){
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Please verify your email first");
            body.put("username", user.getUsername());
            return body;
        }
        if(!user.isAccountApproved()){
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Account not approved");
            return body;
        }
        if(user.getEmailVerified()){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            jwtAuthenticationResponse.setToken(jwt);
            List<Product> products = productRepository.findAll();
            List<ProductCategory> categories = productCategoryRepository.findAll();
            Map<String, Object> body = new HashMap<>();
            body.put("token", jwt);
            body.put("username", loginRequest.getUsername());
            body.put("message","Successful");
            return body;
        } catch (AuthenticationException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "Wrong credentials");
            return body;
        }
    }
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Account not approved");
    return body;
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public String deleteUser(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        userRepository.delete(userRepository.findById(id).orElseThrow(()->new Exception("User Not found") ));
        return "deleted";
    }

    public Map<String, String> createUser(User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {

            Map<String, String> body = new HashMap<>();
            body.put("message", "Please ensure that all fields are correctly filled");
            return body;
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            Map<String, String> body = new HashMap<>();
            body.put("message", "A user with the username already exists");

            return body;
        }

        if (userRepository.existsByEmail(user.getEmail())) {

            Map<String, String> body = new HashMap<>();
            body.put("message", "A user with the email already exists");
            return body;
        }

        if (!Validation.isValidEmail(user.getEmail())) {

            Map<String, String> body = new HashMap<>();
            body.put("message", "Invalid Email");
            return body;
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(UserRole.MANAGER);


        String otp = Validation.generateOTP();
        user.setOTP(otp);
        User createdUser = userRepository.save(user);

        String message = "Please use the following one-time password for verification: " + otp;
        validation.sendEmail(user.getEmail(), "OTP VALIDATION", message);

        Map<String, String> body = new HashMap<>();
        body.put("username", createdUser.getUsername());
        body.put("message", "Check email for one-time password for verification");
        return body;
    }

    public Map<String, String> resendOTP(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String message = "Please use the following one-time password for verification: " + user.getOTP();
            validation.sendEmail(user.getEmail(), "OTP VALIDATION", message);

            Map<String, String> body = new HashMap<>();
            body.put("username", user.getUsername());
            body.put("message", "Check email for one-time password for verification");
            return body;
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
    public Map<String,String> validateOTP(String otp,String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Map<String, String> body = new HashMap<>();
            if(user.getOTP().equals(otp)){
                user.setEmailVerified(true);
                userRepository.save(user);
                body.put("username", user.getUsername());
                body.put("message", "Verification Successful");
                return body;
            }
            else{
                body.put("username", user.getUsername());
                body.put("message", "Invalid OTP");
                return body;
            }
        }
        Map<String, String> body = new HashMap<>();
        body.put("message", "Invalid Username");
        return body;
    }
    public Object checkToken(String token) throws Exception{
        if(jwtUtil.validateToken(token)){
            Map<String, String> body = new HashMap<>();
            body.put("message", "valid");
            return body;
        }
        else{
            throw new Exception("Invalid Token");
        }
    }
    public User changeStatus(Long id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(()-> new Exception("Üser Not Found"));
        user.setAccountApproved(user.isAccountApproved()?false:true);
        userRepository.save(user);
        return user;
    }

}
