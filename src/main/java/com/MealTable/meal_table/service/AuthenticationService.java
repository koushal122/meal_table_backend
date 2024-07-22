//package com.MealTable.meal_table.service;
//
//import com.MealTable.meal_table.exceptions.NotValidRoleException;
//import com.MealTable.meal_table.exceptions.UserAlreadyExist;
//import com.MealTable.meal_table.exceptions.UserNotFoundException;
//import com.MealTable.meal_table.model.User;
//import com.MealTable.meal_table.payloads.LoginRequest;
//import com.MealTable.meal_table.payloads.SignUpRequest;
//import com.MealTable.meal_table.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import javax.security.auth.login.CredentialException;
//
////@Service
//public class AuthenticationService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    public AuthenticationService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder=passwordEncoder;
//    }
//
//    public void authenticateUser(LoginRequest loginRequest) throws Exception {
//        User user = userRepository.findByUserEmail(loginRequest.getUserEmail());
//        if(user==null) throw new UserNotFoundException("User not found with email "+loginRequest.getUserEmail());
//        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//            throw new CredentialException("Invalid password");
//        }
//        boolean roleMatches = user.getRole().stream()
//                .anyMatch(role -> role.equals(loginRequest.getRole().toUpperCase()));
//        if (!roleMatches) {
//            throw new NotValidRoleException("User does not have the required role: " + loginRequest.getRole());
//        }
//    }
//
//    public void createUser(SignUpRequest signUpRequest) throws Exception{
//        User user=userRepository.findByUserEmail((signUpRequest.getUserEmail()));
//        if(user!=null) throw new UserAlreadyExist("User is already registered with Email "+signUpRequest.getUserEmail());
//        userRepository.save(new User(signUpRequest.getUserName(), signUpRequest.getUserEmail(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getRole()));
//    }
//}
