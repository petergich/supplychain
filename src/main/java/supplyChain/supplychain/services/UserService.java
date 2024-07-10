package supplyChain.supplychain.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import supplyChain.supplychain.entities.Product;
import supplyChain.supplychain.entities.ProductCategory;
import supplyChain.supplychain.entities.User;
import supplyChain.supplychain.repositories.ProductCategoryRepository;
import supplyChain.supplychain.repositories.ProductRepository;
import supplyChain.supplychain.repositories.UserRepository;
import supplyChain.supplychain.entities.UserRole;
import supplyChain.supplychain.security.JWTAuthenticationFilter;
import supplyChain.supplychain.security.JWTAuthenticationResponse;
import supplyChain.supplychain.users.LoginRequest;
import supplyChain.supplychain.users.Validation;
import supplyChain.supplychain.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Object login(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            jwtAuthenticationResponse.setToken(jwt);
            List<Product> products = productRepository.findAll();
            List<ProductCategory> categories = productCategoryRepository.findAll();
            Map<String, Object> body = new HashMap<>();
            body.put("token",jwt);
            body.put("username", loginRequest.getUsername());
            body.put("products",products);
            body.put("categories",categories);
            return body;
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("BAD CREDENTIALS");
        }
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }

    public Map<String, String> createUser(User user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getRole() == null || user.getPassword() == null ||
                user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Please ensure that all fields are correctly filled");
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("A user with the username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("A user with the email already exists");
        }

        if (!Validation.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid Email");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Set<UserRole> role = user.getRole();
        String otp = Validation.generateOTP();
        user.setOTP(otp);
        User createdUser = userRepository.save(user);

        String message = "Please use the following one-time password for verification: " + otp;
        validation.sendEmail(user.getEmail(), "OTP VALIDATION", message);

        List<Product> products = productRepository.findAll();

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
    public Map<String,String> validateOTP(String otp,String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Map<String, String> body = new HashMap<>();
            if(user.getOTP().equals(otp)){
                user.setEmailVerified(true);
                body.put("username", user.getUsername());
                body.put("status", "Verification Successful");
                return body;
            }
            else{
                body.put("username", user.getUsername());
                body.put("status", "Invalid OTP");
                return body;
            }
        }
        Map<String, String> body = new HashMap<>();
        body.put("message", "Invalid Email. Please check the Email and try again");
        return body;
    }
}
