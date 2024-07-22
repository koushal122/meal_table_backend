package com.MealTable.meal_table.service;


import com.MealTable.meal_table.exceptions.UserNotFoundException;
import com.MealTable.meal_table.model.Review;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.repository.ReviewRepository;
import com.MealTable.meal_table.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Review> getTopReviews() {
        return reviewRepository.findTop10ByOrderByRatingDesc();
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviewsForUser(String userEmail) {
        userRepository.findByUserEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));
        return reviewRepository.findByUserEmail(userEmail);
    }

    public List<Review> getLowestReviews() {
        return reviewRepository.findTop10ByOrderByRatingAsc();
    }
}
