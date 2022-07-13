package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<User> optional = authService.findByCredentials(loginRequest.getEmail(), loginRequest.getPassword());

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        optional.get().setPassword("");
        session.setAttribute("user", optional.get());

        return ResponseEntity.ok(optional.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User created = new User(0,
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.isAdmin());
                created = authService.register(created);
                if(created == null){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
                }else {
                created.setPassword("");
        return ResponseEntity.status(HttpStatus.CREATED).body(created);}
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<Integer> checkLogin(HttpSession session) {

        User u = (User)session.getAttribute("user");
        if(u == null){
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else  if(!u.isAdmin()){
            return ResponseEntity.status(HttpStatus.OK).body(2);
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(3);
        }
    }
}
