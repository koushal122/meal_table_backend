package com.MealTable.meal_table.service;


import com.MealTable.meal_table.exceptions.UserAlreadyExist;
import com.MealTable.meal_table.exceptions.UserNotFoundException;
import com.MealTable.meal_table.model.Address;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.AdminSignUpRequest;
import com.MealTable.meal_table.payloads.SignUpRequest;
import com.MealTable.meal_table.repository.UserRepository;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;

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

    public User createAdminUser(AdminSignUpRequest adminSignUpRequest,String requestedAdminEmail) throws UserAlreadyExist{
        if(isUserAlreadyRegistered(adminSignUpRequest.getNewAdminEmail())) throw new UserAlreadyExist("User Already Registered with email "+adminSignUpRequest.getNewAdminEmail());
        User user=new User();
        user.setRole("ADMIN");
        user.setUserEmail(adminSignUpRequest.getNewAdminEmail());
        user.setUserName(adminSignUpRequest.getAdminName());
        user.setCreatedBy(requestedAdminEmail);
        user.setCanCreateAdmin(adminSignUpRequest.isCanCreateAdmin());
        user.setPassword(passwordEncoder.encode(adminSignUpRequest.getPassword()));
        return userRepository.save(user);
    }

    public boolean isUserAlreadyRegistered(String userEmail){
        try{
           userRepository.findByUserEmail(userEmail).orElseThrow(()-> new UserNotFoundException("User Not found"));
        }catch (Exception E){
            return false;
        }
        return true;
    }


    public User findUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElseThrow(()->new UserNotFoundException("User not found with email "+userEmail));
    }

    public User isUserAdmin(String userEmail){
        User user = findUserByEmail(userEmail);
        if(!user.getRole().equals("ADMIN")) throw new RuntimeException("You are not authorized to perform this task");
        return user;
    }

    public String getUserNameFormEmail(String userEmail){
        User user = userRepository.findByUserEmail(userEmail).orElseThrow(()-> new UserNotFoundException("User not found with email "+userEmail));
        return  user.getUserName();
    }

    public boolean isAdminRequest(HttpServletRequest request){
        isUserAdmin(jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromHeader(request)));
        return true;
    }

    public User getUser(String userEmail){
        return userRepository.findByUserEmail(userEmail).orElseThrow(()->new RuntimeException("User Not found"));
    }

    public List<Address> getAllAddresses(String userEmail){
        User user=getUser(userEmail);
        return user.getAddresses();
    }

    public void addAddress(User user,Address address){
        List<Address> previousAddress=user.getAddresses();
        previousAddress.add(address);
        user.setAddresses(previousAddress);
        userRepository.save(user);
    }

    public void isAdminCanCreateAdmin(User user) throws AccessDeniedException {
        if(!user.getCanCreateAdmin()) throw new AccessDeniedException("You don't have permission to create admin.");
    }

    public List<User> getAllCreatedAdmins(User user){
        return userRepository.getOtherAdminCreatedByCurrentAdmin(user.getUserEmail());
    }
}
