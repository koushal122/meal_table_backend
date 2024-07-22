package com.MealTable.meal_table.service;

import com.MealTable.meal_table.exceptions.UserNotFoundException;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        //load user from DB
        return userRepository.findByUserEmail(userEmail).orElseThrow(()->new UserNotFoundException("User not found with email "+userEmail));
    }
}
