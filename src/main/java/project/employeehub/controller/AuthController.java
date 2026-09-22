package project.employeehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.employeehub.dto.LoginRequest;
import project.employeehub.entity.User;
import project.employeehub.repository.UserRepository;
import project.employeehub.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository; //to find the username

    @Autowired
    private PasswordEncoder passwordEncoder; //to find the hashed password

    @Autowired
    private JwtUtil jwtUtil; //Now, the AuthController will use JWT.

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        System.out.println("LOGIN METHOD REACHED");

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow();

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),//raw password
                user.getPassword())) {  //StoredHash

            return "Invalid username or password";
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return token; //generates the token
    }

}

/*POST /api/auth/login
        ↓
JSON:
username = john
        password = 1234
        ↓
@RequestBody
        ↓
LoginRequest object
        ↓
findByUsername("john")
        ↓
Get User from database
        ↓
Get stored BCrypt password
        ↓
matches("1234", storedHash)
        ↓
                ┌───────────────┐
                │               │
FALSE            TRUE
       │               │
               ↓               ↓
Invalid            login
password             ↓
        "john"  */
