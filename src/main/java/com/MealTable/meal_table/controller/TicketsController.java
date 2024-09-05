package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.CreateTicketRequest;
import com.MealTable.meal_table.service.TicketService;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController {
    @Autowired
    TicketService ticketService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    @PostMapping(value = "/create-ticket")
    public ResponseEntity<?> createTicket(HttpServletRequest request, @RequestBody  CreateTicketRequest createTicketRequest){
        try{
            User user=userService.getUser(jwtUtils.getUserNameFromRequest(request));
            ticketService.createTicket(user,createTicketRequest);
            return ResponseEntity.status(200).body("Ticket has been created");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/resolve-ticket")
    public ResponseEntity<?> resolveTicket(HttpServletRequest request, @RequestParam long ticketId){
        try{
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            ticketService.resolveTicket(ticketId);
            return ResponseEntity.status(200).body("Ticket has been marked as resolved");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-open-tickets")
    public ResponseEntity<Object> getAllOpenTicket(HttpServletRequest request){
        try{
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            return ResponseEntity.status(200).body(ticketService.getAllOpenTickets());
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-resolved-tickets")
    public ResponseEntity<Object> getAllResolvedTicket(HttpServletRequest request){
        try{
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            return ResponseEntity.status(200).body(ticketService.getAllResolvedTickets());
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-user-ticket")
    public ResponseEntity<Object> getAllUserTicket(HttpServletRequest request){
        try{
            User user=userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            return ResponseEntity.status(200).body(ticketService.getTicketForUser(user));
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
