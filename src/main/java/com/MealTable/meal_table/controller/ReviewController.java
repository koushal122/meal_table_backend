package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Review;
import com.MealTable.meal_table.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/get-top-reviews")
    public List<Review> getTopReviews() {
        return reviewService.getTopReviews();
    }

    @PostMapping("/add-review")
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/get-reviews-by-user")
    public List<Review> getReviewsByUser(@RequestParam String userEmail) {
        return reviewService.getAllReviewsForUser(userEmail);
    }

    @GetMapping("/get-lowest-reviews")
    public List<Review> getLowestReviews() {
        return reviewService.getLowestReviews();
    }

}
