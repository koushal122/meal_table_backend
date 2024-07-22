package com.MealTable.meal_table.exceptions;


import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String message){
        super(message);
    }
}
