package project.employeehub.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

//We'll create a new class that sits in the Spring Security Filter Chain and checks incoming requests for a JWT.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil; //injstcs JWT in this class.

    @Override
    protected void doFilterInternal( //method inherited from OncePerRequestFilter class from Spring Security
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        //"Does this HTTP request contain an Authorization header? If so, give me its value."

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //"If there is no Authorization header, OR it doesn't start with Bearer ,
        // don't try to process a JWT. Just let the request continue through the filter chain."

        String token = authHeader.substring(7);//extract the actual JWT


        try { // Checks if JWT token is invalid or expired.
            String username = jwtUtil.extractUsername(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //  continue the request
        filterChain.doFilter(request, response);
    }
}
