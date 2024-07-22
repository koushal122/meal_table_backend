package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Cart;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.service.CartService;
import com.MealTable.meal_table.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam String userEmail, @RequestParam int productId, @RequestParam int quantity) {
        User user = userService.findUserByEmail(userEmail);
        Cart updatedCart = cartService.addProductToCart(user, productId, quantity);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Cart> updateProductQuantity(@RequestParam String userEmail, @RequestParam int productId, @RequestParam int quantity) {
        User user = userService.findUserByEmail(userEmail);
        Cart updatedCart = cartService.updateProductQuantity(user, productId, quantity);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam String userEmail, @RequestParam int productId) {
        User user = userService.findUserByEmail(userEmail);
        Cart updatedCart = cartService.removeProductFromCart(user, productId);
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<Integer> getNumberOfItemsInCart(@RequestParam String email) {
        User user = userService.findUserByEmail(email);
        int numberOfItems = cartService.getNumberOfItemsInCart(user);
        return new ResponseEntity<>(numberOfItems, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Cart> getCartByUser(@RequestParam String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        Cart cart = cartService.getCartByUser(user);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}

