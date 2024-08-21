package supplyChain.supplychain.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import supplyChain.supplychain.entities.User;
import supplyChain.supplychain.repositories.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetaislService implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetaislService(UserRepository userRepository){
        this.userRepository = userRepository;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with the username: " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
