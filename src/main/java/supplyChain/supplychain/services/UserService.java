package supplyChain.supplychain.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import supplyChain.supplychain.dto.PasswordResetDetails;
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
    public String forgetPassword(String email)throws Exception{
        User user = userRepository.findByEmail(email).orElseThrow(()-> new Exception("User with the email was not found"));
        String token = jwtUtil.generateResetPasswordJwtToken(email);
        String to = email;

        // Subject of the email
        String subject = "Password Reset Request";

        // Password reset link (usually contains a token)
        String resetLink = "http://192.168.254.100:3000/passwordreset/?token="+token;
        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Password Reset Request</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; }\n" +
                "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }\n" +
                "        .header { background-color: #f4f4f4; padding: 10px; text-align: center; }\n" +
                "        .content { padding: 20px; background-color: #ffffff; border: 1px solid #ddd; }\n" +
                "        .footer { font-size: 0.9em; color: #888; text-align: center; padding: 10px; }\n" +
                "        .button { display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Password Reset Request</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Hello {{name}},</p>\n" +
                "            <p>We received a request to reset the password for your account.</p>\n" +
                "            <p>To reset your password, please click the link below:</p>\n" +
                "            <p><a href=\"{{resetLink}}\" class=\"button\">Reset Your Password</a></p>\n" +
                "            <p>If you did not request a password reset, please ignore this email.</p>\n" +
                "            <p>Thank you!</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>If you have any questions, please contact us at support@example.com.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        message = message.replace("{{name}}", user.getUsername());
        message = message.replace("{{resetLink}}", resetLink);
        validation.sendEmail(to, subject, message);
        return "Email sent";
    }
    public String resetPassword(PasswordResetDetails passwordResetDetails) throws Exception{
        String token = passwordResetDetails.getToken();
        User user = userRepository.findByEmail(jwtUtil.getUsernameFromToken(token)).orElseThrow(()->new Exception("User with the email was not found"));
       if(jwtUtil.validateToken(token)) {
            user.setPassword(passwordEncoder.encode(passwordResetDetails.getPassword()));

            userRepository.save(user);
            return "Password reset successful";
        }else{
           return "Password reset failed";
       }
    }

}
