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

    public Review addReview(double rating,String review,User user) {
        Review reviewObj=new Review();
        reviewObj.setUser(user);
        reviewObj.setRating(rating);
        reviewObj.setReview(review);
        return reviewRepository.save(reviewObj);
    }

    public List<Review> getAllReviewsForUser(User user) {
        return reviewRepository.findByUser(user);
    }

    public List<Review> getLowestReviews() {
        return reviewRepository.findTop10ByOrderByRatingAsc();
    }
}
