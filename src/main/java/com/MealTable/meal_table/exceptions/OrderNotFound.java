package com.MealTable.meal_table.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OrderNotFound extends RuntimeException{
    public OrderNotFound(String message){
        super(message);
    }
}
