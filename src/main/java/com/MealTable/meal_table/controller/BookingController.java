package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Booking;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.BookingRequest;
import com.MealTable.meal_table.service.BookingService;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.BookingStatus;
import com.MealTable.meal_table.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @GetMapping("/available-tables")
    public Map<Integer, Integer> getAvailableTables(HttpServletRequest request, @RequestParam long startTime, @RequestParam long endTime) {
        return bookingService.getAvailableTables(startTime,endTime);
    }

    @PostMapping("/book-tables")
    public ResponseEntity<Object> bookTables(HttpServletRequest request, @RequestBody BookingRequest bookingRequest) {
        try {
            Booking booking=bookingService.bookTables(jwtUtils.getUserNameFromRequest(request),bookingRequest);
            return ResponseEntity.status(200).body(booking);
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping("/user-future-bookings")
    public List<Booking> getFutureBookingsByUser(HttpServletRequest request) {
        String userEmail= jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromHeader(request));
        return bookingService.getFutureBookingsByUserEmail(userEmail);
    }

    @GetMapping("/user-booking-history")
    public List<Booking> getUserBookingHistory(HttpServletRequest request) {
        User user=userService.getUser(jwtUtils.getUserNameFromRequest(request));
        return bookingService.getUserBooking(user);
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
    public ResponseEntity<?> completeBooking(HttpServletRequest request,@RequestParam int bookingId) {
        try {
            userService.isUserAdmin(jwtUtils.getUserNameFromRequest(request));
            bookingService.completeBooking(bookingId);
            return ResponseEntity.ok().body("Booking has been completed");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }


    @GetMapping(value = "/get-user-bookings")
    public List<Booking> getUserBooking(HttpServletRequest request){
        String userEmail = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromHeader(request));
       return bookingService.getAllBookingByUserEmail(userEmail);
    }

    @GetMapping(value = "/admin/start-booking")
    public ResponseEntity<String> startBooking(HttpServletRequest request, @RequestParam int bookingId){
        try {
            userService.isAdminRequest(request);
            bookingService.startBooking(bookingId);
        }catch (Exception exception){
            return ResponseEntity.status(500).body(exception.getMessage());
        }
        return null;
    }

    @GetMapping(value = "/get-all-upcoming-booking")
    public ResponseEntity<Object> getAllUpcomingBooking(HttpServletRequest request,@RequestParam long startTime){
        try{
            userService.isAdminRequest(request);
            List<Booking> bookings =bookingService.getAllUpcomingBookedBookings(startTime);
            return ResponseEntity.status(200).body(bookings);
        }catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping(value = "/get-all-active-booking")
    public ResponseEntity<Object> getAllActiveBooking(HttpServletRequest request){
        try{
            userService.isAdminRequest(request);
            List<Booking> bookings =bookingService.getBookingByStatus(BookingStatus.ACTIVE);
            return ResponseEntity.status(200).body(bookings);
        }catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
