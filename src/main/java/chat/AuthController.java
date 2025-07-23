package chat;

import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final Key SECRET_KEY;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
        String secretString = "f832h293hd29d8h29djwq9djqw9djqw9d"; // минимум 32 символа!
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        String user = body.get("username");
        String pass = body.get("password");

        if (userRepo.existsByUsername(user)) {
            return "User exists";
        }

        userRepo.save(new User(user, pass));
        return "Registered";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String user = body.get("username");
        String pass = body.get("password");

        User u = userRepo.findByUsername(user);
        if (u != null && u.getPassword().equals(pass)) {
            String token = Jwts.builder()
                    .setSubject(user)
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 день
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid credentials");
        return error;
    }
}
