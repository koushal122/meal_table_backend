package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Review;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.ReviewRequest;
import com.MealTable.meal_table.service.ReviewService;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/get-top-reviews")
    public List<Review> getTopReviews() {
        return reviewService.getTopReviews();
    }

    @PostMapping("/add-review")
    public ResponseEntity<String> addReview(HttpServletRequest request,@RequestBody ReviewRequest reviewRequest) {
        try{
            User user=userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            reviewService.addReview(reviewRequest.getRating(),reviewRequest.getReview(),user);
            return ResponseEntity.status(200).body("Review Added successfully");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }

    @GetMapping("/get-reviews-by-user")
    public ResponseEntity<?> getReviewsByUser(HttpServletRequest request) {
        try{
            User user=userService.findUserByEmail(jwtUtils.getUserNameFromRequest(request));
            return ResponseEntity.status(200).body(reviewService.getAllReviewsForUser(user));
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }

    }

    @GetMapping("/get-lowest-reviews")
    public List<Review> getLowestReviews() {
        return reviewService.getLowestReviews();
    }

}
