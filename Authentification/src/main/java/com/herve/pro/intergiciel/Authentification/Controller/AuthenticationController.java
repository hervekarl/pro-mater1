package com.herve.pro.intergiciel.Authentification.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.herve.pro.intergiciel.Authentification.DTO.LoginDto;
import com.herve.pro.intergiciel.Authentification.DTO.SignUpDto;
import com.herve.pro.intergiciel.Authentification.DTO.Response.LoginResponse;
import com.herve.pro.intergiciel.Authentification.Modeles.Users;
import com.herve.pro.intergiciel.Authentification.Services.JwtService;
import com.herve.pro.intergiciel.Authentification.Services.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    
    private final UsersService usersService;

    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<Users> createUsers(@RequestBody SignUpDto user) {

        Users usercreate = usersService.create(user);
        if (usercreate == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(usercreate);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginDto) {
        Users userAuthenticate = usersService.authenticate(loginDto);
        String jwtToken = jwtService.generateToken(userAuthenticate);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        System.out.println("JWT = " + jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/all/users")
    public ResponseEntity<?> getAllUsers() {
        if (usersService.findAll() == null) {
            return ResponseEntity.badRequest().body("No users found");
        }
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return usersService.findUserById(id)
                .map(user -> ResponseEntity.ok((Object) user)) // 👈 cast explicite
                .orElseGet(() -> ResponseEntity.badRequest().body((Object) ("User not found with id: " + id))); // 👈
                                                                                                                // cast
    }

    @GetMapping("/user/username/{username}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String username) {
        Users user = usersService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("User not found with username: " + username);
    }

}
