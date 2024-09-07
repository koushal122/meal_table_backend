package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     Optional<User> findByUserEmail(String userEmail);
     @Query("SELECT u FROM User u WHERE u.createdBy = :currentAdmin")
     List<User> getOtherAdminCreatedByCurrentAdmin(@Param("currentAdmin") String currentAdmin);
}
