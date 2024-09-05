package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.Order;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.util.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    List<Order> findByOrderStatus(OrderStatus status);
}
