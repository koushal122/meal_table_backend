package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Address;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-details")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @GetMapping(value = "/get-all-address")
    public ResponseEntity<?> getAllAddress(HttpServletRequest request){
        try{
            userService.getUser(jwtUtils.getUserNameFromRequest(request));
            return ResponseEntity.status(200).body(userService.getAllAddresses(jwtUtils.getUserNameFromRequest(request)));
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping(value = "/add-address")
    public ResponseEntity<?> addAddress(HttpServletRequest request, @RequestBody Address address){
        try {
            User user=userService.getUser(jwtUtils.getUserNameFromRequest(request));
            userService.addAddress(user,address);
            return ResponseEntity.status(200).body("Address has been added");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
