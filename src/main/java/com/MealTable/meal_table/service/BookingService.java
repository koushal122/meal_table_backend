package com.MealTable.meal_table.service;

import com.MealTable.meal_table.exceptions.ResourceNotFoundException;
import com.MealTable.meal_table.model.Booking;
import com.MealTable.meal_table.model.TableEntity;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.repository.BookingRepository;
import com.MealTable.meal_table.repository.TableRepository;
import com.MealTable.meal_table.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TableService tableService;

    @Autowired
    private UserService userService;

    // Mock method to simulate payment processing
    private void processPayment(Booking booking) {
        booking.setPaymentStatus("PAID");
        bookingRepository.save(booking);
    }

    // Mock method to simulate refund processing
    private void processRefund(Booking booking) {
        booking.setPaymentStatus("REFUNDED");
        bookingRepository.save(booking);
    }

    public List<List<Integer>> getAvailableTables() {
        List<TableEntity> allTables = tableRepository.findAll();
        List<List<Integer>> availableTableWithSeats=new ArrayList<>();
        for (TableEntity table : allTables) {
            availableTableWithSeats.add(List.of(table.getId(),table.getAvailableSeats()));
        }
        return availableTableWithSeats;
    }

    public int getAvailableSeats() {
        List<TableEntity> allTables = tableRepository.findAll();
        int totalSeats=0;
        for (TableEntity table : allTables) {
           totalSeats+=table.getAvailableSeats();
        }
        return totalSeats;
    }

    public Booking bookTables(String email, int requiredSeats, LocalDateTime startTime, LocalDateTime endTime, double paymentAmount) {
        User user = userRepository.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        int totalSeats=getAvailableSeats();
        if (totalSeats<requiredSeats) {
            throw new ResourceNotFoundException("No available tables meet the required seat count.");
        }
        List<List<Integer>> availableTablesWithSeats=getAvailableTables();
        availableTablesWithSeats.sort(new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o2.get(1) - o1.get(1);
            }
        });
        List<List<Integer>> tableIdsWithSeats=new ArrayList<>();
        int ind=0;
        while(requiredSeats>0){
            int availableSeatInCurrentTable=availableTablesWithSeats.get(ind).get(1);
            int requiredSeatInCurrentTable= Math.min(requiredSeats, availableSeatInCurrentTable);
            tableService.bookSeatsInTable(availableTablesWithSeats.get(ind).get(0),requiredSeatInCurrentTable);
            requiredSeats-=requiredSeatInCurrentTable;
            tableIdsWithSeats.add(List.of(availableTablesWithSeats.get(ind).getFirst(),requiredSeatInCurrentTable));
            ind++;
        }
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTableIdsWithSeats(tableIdsWithSeats);
        booking.setSeats(requiredSeats);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setStatus("BOOKED");
        booking.setPaymentAmount(paymentAmount);
        processPayment(booking);
        return bookingRepository.save(booking);
    }

    public List<Booking> getFutureBookingsByUserEmail(String userEmail) {
        return bookingRepository.findFutureBookingsByUserEmail(userEmail);
    }

    public List<Booking> getTopBookings(int limit) {
        Pageable top = PageRequest.of(0, limit);
        return bookingRepository.findAll(top).getContent();
    }

    public List<Booking> getTopBookingsWithLowestRating(int limit) {
        Pageable top = PageRequest.of(0, limit, Sort.by("rating").ascending());
        return bookingRepository.findAll(top).getContent();
    }

    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!"BOOKED".equals(booking.getStatus())) {
            throw new IllegalStateException("Only booked bookings can be cancelled.");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        long timeDifference = booking.getStartTime().getHour() - currentTime.getHour();
        //if cancel time is more than 1 hour then only we will process refund
        //TODO:Need to implement refund process
        if (timeDifference > 1) {
            processRefund(booking);
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        freeUpTables(booking.getTableIdsWithSeats());
    }

    public void completeBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (!"BOOKED".equals(booking.getStatus())) {
            throw new IllegalStateException("Only booked bookings can be completed.");
        }
        userService.isUserAdmin(booking.getUser().getUserEmail());
        booking.setStatus("COMPLETED");
        bookingRepository.save(booking);
        freeUpTables(booking.getTableIdsWithSeats());
    }

    public void freeUpTables(List<List<Integer>> tablesWithSeats){
        for(List<Integer> table:tablesWithSeats){
            tableService.freeUpTableSeat(table.getFirst(),table.getLast());
        }
    }
}
