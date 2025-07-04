// package com.herve.pro.intergiciel.Authentification.Controller;

// // import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.herve.pro.intergiciel.Authentification.DTO.LoginDto;
// import com.herve.pro.intergiciel.Authentification.DTO.SignUpDto;
// import com.herve.pro.intergiciel.Authentification.DTO.Response.LoginResponse;
// import com.herve.pro.intergiciel.Authentification.Modeles.Users;
// import com.herve.pro.intergiciel.Authentification.Services.JwtService;
// import com.herve.pro.intergiciel.Authentification.Services.UsersService;

// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import lombok.Setter;

// @RequestMapping("/authicate")
// @RestController
// @Getter
// @Setter
// @RequiredArgsConstructor
// public class Authentication {
//     private final JwtService jwtService;
    
//     private final UsersService usersService;



//     @PostMapping("/signup")
//     public ResponseEntity<Users> register(@RequestBody SignUpDto registerUserDto) {
//         Users registeredUser = usersService.create (registerUserDto);

//         return ResponseEntity.ok(registeredUser);
//     }

//     @PostMapping("/login")
//     public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
//         Users authenticatedUser = usersService.authenticate(loginUserDto);

//         String jwtToken = jwtService.generateToken(authenticatedUser);

//         LoginResponse loginResponse = new LoginResponse();
//         loginResponse.setToken(jwtToken);
//         loginResponse.setExpiresIn(jwtService.getExpirationTime());

//         return ResponseEntity.ok(loginResponse);
//     }
// }
