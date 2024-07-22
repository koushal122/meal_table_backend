package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.Review;
import com.MealTable.meal_table.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findTop10ByOrderByRatingDesc();
    List<Review> findTop10ByOrderByRatingAsc();
    List<Review> findByUserEmail(String userEmail);
}
