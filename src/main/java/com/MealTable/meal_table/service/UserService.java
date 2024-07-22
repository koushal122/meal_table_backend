package com.MealTable.meal_table.service;


import com.MealTable.meal_table.exceptions.UserAlreadyExist;
import com.MealTable.meal_table.exceptions.UserNotFoundException;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.AdminSignUpRequest;
import com.MealTable.meal_table.payloads.SignUpRequest;
import com.MealTable.meal_table.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(SignUpRequest signUpRequest){
        if(isUserAlreadyRegistered(signUpRequest.getUserEmail())) throw new UserAlreadyExist("User Already Registered with email "+signUpRequest.getUserEmail());
        User user=new User();
        user.setRole("USER");
        user.setUserEmail(signUpRequest.getUserEmail());
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(user);
    }

    public User createAdminUser(AdminSignUpRequest adminSignUpRequest){
        if(isUserAlreadyRegistered(adminSignUpRequest.getUserEmail())) throw new UserAlreadyExist("User Already Registered with email "+adminSignUpRequest.getUserEmail());
        User user=new User();
        user.setRole("ADMIN");
        user.setUserEmail(adminSignUpRequest.getUserEmail());
        user.setUserName(adminSignUpRequest.getUserName());
        user.setPassword(passwordEncoder.encode(adminSignUpRequest.getPassword()));
        return userRepository.save(user);
    }

    public boolean isUserAlreadyRegistered(String userEmail){
        try{
           User user=userRepository.findByUserEmail(userEmail).orElseThrow(()-> new UserNotFoundException("User Not found"));
        }catch (Exception E){
            return false;
        }
        return true;
    }


    public User findUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElseThrow(()->new UserNotFoundException("User not found with email "+userEmail));
    }

    public void isUserAdmin(String userEmail){
        User user = findUserByEmail(userEmail);
        if(!user.getRole().equals("ADMIN")) throw new RuntimeException("You are not authorized to perform this task");
    }

    public String getUserNameFormEmail(String userEmail){
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(()-> new UserNotFoundException("User not found with email "+userEmail));
        return  user.getUserName();
    }

}
