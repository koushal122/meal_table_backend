package com.MealTable.meal_table.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }
}
