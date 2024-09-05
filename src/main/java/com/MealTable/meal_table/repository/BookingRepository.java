package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.Booking;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.util.BookingStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    @Query("SELECT b FROM Booking b WHERE b.user.userEmail = :userEmail AND b.status = :status")
    List<Booking> findFutureBookingsByUserEmail(@Param("userEmail") String userEmail, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.user.userEmail = :userEmail")
    List<Booking> findAllBookingByUserEmail(@Param("userEmail") String userEmail);

    List<Booking> getByUser(User user);

    @Query(value = "SELECT b FROM Booking b WHERE (b.startTime < :requestedEndTime AND b.startTime > :requestedStartTime) OR (b.endTime < :requestedEndTime AND b.endTime > :requestedStartTime) OR (b.startTime = :requestedStartTime) OR (b.endTime = :requestedEndTime)")
    List<Booking> getBookingBetweenStartTimeAndEndTime(@Param("requestedStartTime") long requestedStartTime,@Param("requestedEndTime") long requestedEndTime);

    @Query(value = "SELECT b FROM Booking b WHERE b.startTime >= :requestedStartTime AND b.status = :status")
    List<Booking> getAllUpcomingBooking(@Param("requestedStartTime") long requestedStartTime,@Param("status") BookingStatus status);

    List<Booking> findByStatus(BookingStatus status);
}
