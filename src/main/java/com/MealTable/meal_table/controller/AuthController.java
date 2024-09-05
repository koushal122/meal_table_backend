package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.AdminSignUpRequest;
import com.MealTable.meal_table.payloads.LoginRequest;
import com.MealTable.meal_table.payloads.SignUpRequest;
import com.MealTable.meal_table.response.LoginResponse;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager,JwtUtils jwtUtils) {
        this.authenticationManager=authenticationManager;
        this.jwtUtils=jwtUtils;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<Object> initialAuthentication(@Valid  @RequestBody LoginRequest loginRequest){
        Authentication authentication;
        try {
            logger.info("Received authentication request for email {}", loginRequest.getUserEmail());
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getPassword()));
            logger.info("Authenticated successfully");
        } catch (Exception e) {
            logger.info("Something went wrong while authentication {}", e.getMessage());
            return ResponseEntity.status(401).body(e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        if(loginRequest.isAdmin()){
            try{
                userService.isUserAdmin(userDetails.getUsername());
            }catch (Exception e){
                return  ResponseEntity.status(401).body("You are not registered as Admin, please login as user");
            }
        }
        LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken, userService.getUserNameFormEmail(userDetails.getUsername()));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create-user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody SignUpRequest signUpRequest){
        try {
            logger.info("Create user request for email {}", signUpRequest.getUserEmail());
            User user=userService.createUser(signUpRequest);
            return ResponseEntity.status(200).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping(value = "/create-admin")
    public ResponseEntity<Object> createAdmin(HttpServletRequest request, @Valid @RequestBody AdminSignUpRequest adminSignUpRequest){
        try {
            logger.info("Create admin user request for email {}", adminSignUpRequest.getUserEmail());
            userService.isUserAdmin(jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromHeader(request)));
            User user=userService.createAdminUser(adminSignUpRequest);
            return ResponseEntity.status(200).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
