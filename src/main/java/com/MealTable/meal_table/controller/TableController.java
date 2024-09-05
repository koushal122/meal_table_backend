package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.TableEntity;
import com.MealTable.meal_table.service.TableService;
import com.MealTable.meal_table.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create-table")
    public ResponseEntity<?> createTable(HttpServletRequest request,@RequestParam int capacity){
        try{
            userService.isAdminRequest(request);
            TableEntity createdTable=tableService.createTable(capacity);
            return ResponseEntity.status(200).body("Table has been created with id "+createdTable.getId()+" and capacity "+createdTable.getCapacity());
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-table")
    public Object getAllTables(HttpServletRequest request){
        try {
            userService.isAdminRequest(request);
            return ResponseEntity.status(200).body(tableService.getAllTables());
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping(value = "/delete-table")
    public ResponseEntity<?> deleteTable(HttpServletRequest request,@RequestParam int tableId){
        try{
            userService.isAdminRequest(request);
            tableService.deleteTable(tableId);
            return ResponseEntity.status(200).body("table deleted successfully");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
