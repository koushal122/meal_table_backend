package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Order;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.CreateOrderRequest;
import com.MealTable.meal_table.service.OrderService;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create-order")
    public ResponseEntity<Object> createOrder(HttpServletRequest request, @RequestBody CreateOrderRequest createOrderRequest){
        try{
            User user=userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            Order order=orderService.createOrder(user,createOrderRequest);
            return ResponseEntity.status(200).body(order);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/confirm-order")
    public ResponseEntity<Object> confirmOrder(HttpServletRequest request, @RequestParam long orderId){
        try{
            userService.isAdminRequest(request);
            Order order=orderService.confirmOrder(orderId);
            return ResponseEntity.status(200).body(order);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete-order")
    public ResponseEntity<?> deleteOrder(HttpServletRequest request,@RequestParam long orderId){
        try {
            User user=userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            orderService.deleteOrder(orderId);
            return ResponseEntity.status(200).body("Order has been deleted");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-user-order")
    public ResponseEntity<Object> getAllUserOrder(HttpServletRequest request){
        try {
            User user=userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllOrderForUser(user);
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-orders")
    public ResponseEntity<Object> getAllOrders(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-booked-order")
    public ResponseEntity<Object> getAllBookedOrders(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllBookedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-confirm-order")
    public ResponseEntity<Object> getAllConfirmOrders(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllConfirmedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-out-for-pickup-order")
    public ResponseEntity<Object> getAllOutForPickUpOrders(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllOutForReturnPickUpOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-dispatched-order")
    public ResponseEntity<Object> getAllDispatchedOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllDispatchedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-returned-order")
    public ResponseEntity<Object> getAllReturnedOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllReturnedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-return-requested-order")
    public ResponseEntity<Object> getAllReturnRequestedOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllReturnRequestOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-return-rejected-order")
    public ResponseEntity<Object> getAllReturnRejectedOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllReturnRejectedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-cancel-requested-order")
    public ResponseEntity<Object> getAllCancelRequestedOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllCancelRequestedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-cancelled-order")
    public ResponseEntity<Object> getAllCancelledOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllCancelledOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-cancel-rejected-order")
    public ResponseEntity<Object> getAllCancelRejectedOrder(HttpServletRequest request){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            List<Order> allOrders=orderService.getAllCancelRejectedOrder();
            return ResponseEntity.status(200).body(allOrders);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    @PutMapping(value = "/cancel-request")
    public ResponseEntity<Object> cancelOrderRequest(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            orderService.cancelOrderRequest(orderId);
            return ResponseEntity.status(200).body("Order cancellation requested, Kindly wait for approval");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/reject-cancel-request")
    public ResponseEntity<Object> rejectCancelRequest(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.rejectCancelOrder(orderId);
            return ResponseEntity.status(200).body("Order cancellation requested has been rejected");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/approve-cancel-request")
    public ResponseEntity<Object> approveCancelOrder(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.approveCancelOrder(orderId);
            return ResponseEntity.status(200).body("Order cancellation requested has been approved");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/order-delivered")
    public ResponseEntity<Object> markOrderDelivered(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.deliverOrder(orderId);
            return ResponseEntity.status(200).body("Order has been marked as delivered");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/order-dispatched")
    public ResponseEntity<Object> markOrderDispatched(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.dispatchOrder(orderId);
            return ResponseEntity.status(200).body("Order has been marked as Dispatched");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/return-request")
    public ResponseEntity<Object> returnRequest(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            orderService.returnOrderRequest(orderId);
            return ResponseEntity.status(200).body("Return request has been raised, kindly wait for approval");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/reject-return")
    public ResponseEntity<Object> rejectReturnRequest(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.rejectReturnOrder(orderId);
            return ResponseEntity.status(200).body("Return request has been rejected");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    @PutMapping(value = "/return-started")
    public ResponseEntity<Object> approveReturnRequest(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.outForReturnPickUp(orderId);
            return ResponseEntity.status(200).body("Return order has been started");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PutMapping(value = "/returned")
    public ResponseEntity<Object> orderReturned(HttpServletRequest request,@RequestParam long orderId){
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            orderService.orderReturned(orderId);
            return ResponseEntity.status(200).body("Order has been returned");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }







}
