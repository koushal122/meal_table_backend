package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Booking;
import com.MealTable.meal_table.payloads.BookingRequest;
import com.MealTable.meal_table.service.BookingService;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/available-tables")
    public List<List<Integer>> getAvailableTables(HttpServletRequest request) {
        return bookingService.getAvailableTables();
    }

    @PostMapping("/book-tables")
    public Booking bookTables(@RequestBody BookingRequest request) {
        return bookingService.bookTables(request.getEmail(), request.getRequiredSeats(), request.getStartTime(), request.getEndTime(), request.getPaymentAmount());
    }

    @GetMapping("/user-future-bookings")
    public List<Booking> getFutureBookingsByUser(@RequestParam String userEmail) {
        return bookingService.getFutureBookingsByUserEmail(userEmail);
    }

    @GetMapping("/top-bookings")
    public List<Booking> getTopBookings(@RequestParam int limit) {
        return bookingService.getTopBookings(limit);
    }

    @GetMapping("/lowest-rating-bookings")
    public List<Booking> getTopBookingsWithLowestRating(@RequestParam int limit) {
        return bookingService.getTopBookingsWithLowestRating(limit);
    }

    @PostMapping("/cancel-booking")
    public ResponseEntity<?> cancelBooking(@RequestParam int bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok().body("Booking has been cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    @PostMapping("/complete-booking")
    public ResponseEntity<?> completeBooking(@RequestParam int bookingId) {
        try {
            bookingService.completeBooking(bookingId);
            return ResponseEntity.ok().body("Booking has been completed");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }
}
