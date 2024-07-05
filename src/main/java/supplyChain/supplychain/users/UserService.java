package supplyChain.supplychain.users;

import supplyChain.supplychain.security.JWTAuthenticationFilter;
import supplyChain.supplychain.security.JWTAuthenticationResponse;
import supplyChain.supplychain.security.JWTUtil;
import supplyChain.supplychain.security.JWTAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.*;

@RestController
@RequestMapping("/users")
public class UserService {
    public Validation validation;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTAuthenticationResponse jwtAuthenticationResponse;
    @Autowired
    private JWTUtil jwtUtil;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtil.generateToken(loginRequest.getUsername());
            jwtAuthenticationResponse.setToken(jwt);
            return ResponseEntity.ok(jwtAuthenticationResponse);
        } catch (AuthenticationException e){
            return new ResponseEntity<>("BAD CREDENTIALS",HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> users= userRepository.findById(id);
        return new ResponseEntity<>(users.get(),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.delete(user.get());
            return new ResponseEntity<String>("Successfull",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("User with the Id not found",HttpStatus.OK);
        }
    }
    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        if(user.getUsername()==null || user.getEmail() == null || user.getRole()== null || user.getPassword() == null || user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
            return new ResponseEntity<>("Please ensure that all the fields are correctly filled",HttpStatus.UNAUTHORIZED);
        }
        if(userRepository.existsByUsername(user.getUsername())){
            return new ResponseEntity<>("A user with the username already exists",HttpStatus.UNAUTHORIZED);
        }
        if(userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("A user with the email already exists",HttpStatus.UNAUTHORIZED);
        }
        if (!Validation.isValidEmail(user.getEmail())){
            return new ResponseEntity<>("Invalid Email",HttpStatus.UNAUTHORIZED);
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Set<UserRole> role=user.getRole();
        String otp = validation.generateOTP();
        user.setOTP(otp);
        User createdUser =userRepository.save(user);

        String message = "Please use the following one time password for verification"+otp;
        validation.sendEmail(user.getEmail(),"OTP VALIDATION",message);
        Map<String, String> body = new HashMap<String, String>() {{
            put("username", createdUser.getUsername());
            put("message", "Check email for one time password for verification");
        }};
        return new ResponseEntity<>(body,HttpStatus.OK);
    }
    @PostMapping("/resendotp")
    public ResponseEntity<?> resendOTP(@RequestBody String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        System.out.println(optionalUser);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String message = "Please use the following one time password for verification" + user.getOTP();
            validation.sendEmail(user.getEmail(), "OTP VALIDATION", message);
            Map<String, String> body = new HashMap<String, String>() {{
                put("username", user.getUsername());
                put("message", "Check email for one time password for verification");
            }};
            return new ResponseEntity<>(body, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("User not found", HttpStatus.OK);
        }
    }

}
