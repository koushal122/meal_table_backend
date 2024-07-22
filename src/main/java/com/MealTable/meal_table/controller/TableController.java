package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.service.TableService;
import com.MealTable.meal_table.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create-table")
    public ResponseEntity<?> createTable(@RequestParam int capacity,@RequestParam String userEmail){
        try{
            userService.isUserAdmin(userEmail);
            tableService.createTable(capacity);
            return ResponseEntity.status(200).body("Table has been created with capacity "+capacity);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
