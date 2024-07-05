package supplyChain.supplychain.security;

import supplyChain.supplychain.users.CustomUserDetaislService;
import supplyChain.supplychain.security.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends OncePerRequestFilter{
    private final JWTUtil jwtUtil;
    private final CustomUserDetaislService userDetaislService;

    public JWTAuthorizationFilter(CustomUserDetaislService userDetaislService, JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.userDetaislService = userDetaislService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");


        String username = null;
        String token = null;

        if (username !=null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
            username = jwtUtil.getUsernameFromToken(token);

        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetaislService.loadUserByUsername(username);


            if (jwtUtil.validateToken(token)){

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}