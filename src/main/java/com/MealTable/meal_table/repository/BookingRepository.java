package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

//    @Query("SELECT b FROM Booking b WHERE b.table.id = :tableId AND b.endTime > CURRENT_TIMESTAMP")
//    List<Booking> findActiveBookingsByTableId(@Param("tableId") int tableId);
//
//    @Query("SELECT b FROM Booking b WHERE b.endTime > CURRENT_TIMESTAMP")
//    List<Booking> findAllActiveBookings();

    @Query("SELECT b FROM Booking b WHERE b.user.userEmail = :userEmail AND b.startTime > CURRENT_TIMESTAMP")
    List<Booking> findFutureBookingsByUserEmail(@Param("userEmail") String userEmail);
}
