package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.OrderProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductQuantityRepository extends JpaRepository<OrderProductQuantity,Long> {
}
