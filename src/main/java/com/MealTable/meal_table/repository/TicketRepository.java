package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.Tickets;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.util.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Tickets,Long> {
    List<Tickets> findByUser(User user);
    List<Tickets> findByTicketStatus(TicketStatus ticketStatus);
}
