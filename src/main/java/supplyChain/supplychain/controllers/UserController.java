package supplyChain.supplychain.controllers;

import supplyChain.supplychain.dto.EmailDto;
import supplyChain.supplychain.dto.PasswordResetDetails;
import supplyChain.supplychain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import supplyChain.supplychain.dto.LoginRequest;
import supplyChain.supplychain.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Object response = userService.login(loginRequest);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try{

            return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            Map<String, String> response = userService.createUser(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/resendotp")
    public ResponseEntity<?> resendOTP(@RequestBody User user) {
        String email= user.getEmail();
        try {
            Map<String, String> response = userService.resendOTP(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/otpverification")
    public ResponseEntity<?> otpVerification(@RequestBody User user){
        String username = user.getUsername();
        String otp = user.getOTP();
        try{
            Map<String, String> response = userService.validateOTP(otp,username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/checktoken")
    public ResponseEntity<?> checkToken(@RequestBody String token){
        try{
            Object response = userService.checkToken(token);
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch(Exception e) {
            Map<String,String> body = new HashMap<>();
            body.put("message",e.getMessage());
            return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long id) {
        try{
            User user =  userService.changeStatus(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/forgetpassword")
    public ResponseEntity<?> userForgetPassword(@RequestBody EmailDto email) {
        try{
            String response = userService.forgetPassword(email.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetDetails details) {
        try{
            String response = userService.resetPassword(details);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
