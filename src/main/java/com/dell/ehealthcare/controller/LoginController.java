package com.dell.ehealthcare.controller;

import com.dell.ehealthcare.exceptions.RoleNotfoundException;
import com.dell.ehealthcare.model.BankAccount;
import com.dell.ehealthcare.model.Role;
import com.dell.ehealthcare.model.User;
import com.dell.ehealthcare.model.enums.Roles;
import com.dell.ehealthcare.payload.request.LoginRequest;
import com.dell.ehealthcare.payload.request.SignupRequest;
import com.dell.ehealthcare.payload.response.MessageResponse;
import com.dell.ehealthcare.repository.BankRepository;
import com.dell.ehealthcare.repository.RoleRepository;
import com.dell.ehealthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BankRepository bankRepository;

    @GetMapping(value = "/")
    public String index() {
        return "login";
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if(user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())){
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found!"));
        }
    }

    @PostMapping("/logout")
    ResponseEntity<MessageResponse> logoutUser() {
        return ResponseEntity.ok(new MessageResponse("You've been logout!"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstname(),
                signUpRequest.getLastname(), signUpRequest.getEmail(), signUpRequest.getPassword(),
                signUpRequest.getDob(), signUpRequest.getPhone(), signUpRequest.getAddress());
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Roles.USER)
                    .orElseThrow(() -> new RoleNotfoundException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if(role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(Roles.ADMIN)
                            .orElseThrow(() -> new RoleNotfoundException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(Roles.USER)
                            .orElseThrow(() -> new RoleNotfoundException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        // Create new bank's account
        BankAccount bank = new BankAccount(signUpRequest.getAccountNum(), 1000.0, user);
        bankRepository.save(bank);

        Optional<User> savedUser = userRepository.findByUsername(signUpRequest.getUsername());

        if(savedUser.isPresent() && savedUser.get().getPassword().equals(signUpRequest.getPassword())){
            return ResponseEntity.ok(savedUser.get().getId());
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found!"));
        }
    }

}
