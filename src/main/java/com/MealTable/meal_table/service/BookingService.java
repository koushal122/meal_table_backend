package com.MealTable.meal_table.service;

import com.MealTable.meal_table.exceptions.ResourceNotFoundException;
import com.MealTable.meal_table.model.Booking;
import com.MealTable.meal_table.model.Seat;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.BookingRequest;
import com.MealTable.meal_table.repository.BookingRepository;
import com.MealTable.meal_table.repository.SeatRepository;
import com.MealTable.meal_table.repository.TableRepository;
import com.MealTable.meal_table.repository.UserRepository;
import com.MealTable.meal_table.util.BookingStatus;
import com.MealTable.meal_table.util.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Autowired
    private SeatRepository seatRepository;

//    // Mock method to simulate payment processing
//    private void processPayment(Booking booking) {
//        booking.setPaymentStatus("PAID");
//        bookingRepository.save(booking);
//    }
//
    // Mock method to simulate refund processing
    private void processRefund(Booking booking) {
        booking.setPaymentStatus(PaymentStatus.REFUNDED);
        bookingRepository.save(booking);
    }

    public Map<Integer, Integer> getAvailableTables(long startTime, long endTime) {
        List<Booking> overlappingBooking=bookingRepository.getBookingBetweenStartTimeAndEndTime(startTime,endTime);
        List<Seat> allSeats=seatRepository.findAll();
        HashSet<Long> unavailableSeatId=new HashSet<>();
        for(Booking booking:overlappingBooking){
            for(Seat seat:booking.getSeatsBooked()){
                unavailableSeatId.add(seat.getId());
            }
        }
        long totalSeatsAvailable=allSeats.size()-unavailableSeatId.size();
        Map<Integer,Integer> availableTablesWithSeats=new HashMap<>();
        for(Seat seat :allSeats){
            if(!unavailableSeatId.contains(seat.getId())){
                availableTablesWithSeats.put(seat.getTable().getId(),availableTablesWithSeats.getOrDefault(seat.getTable().getId(),0)+1);
            }
        }
        return availableTablesWithSeats;
    }

    public List<Seat> getOnlyAvailableSeats(long startTime,long endTime) {
        List<Booking> overlappingBooking=bookingRepository.getBookingBetweenStartTimeAndEndTime(startTime,endTime);
        List<Seat> allSeats=seatRepository.findAll();
        HashSet<Long> unavailableSeatId=new HashSet<>();
        for(Booking booking:overlappingBooking){
            for(Seat seat:booking.getSeatsBooked()){
                unavailableSeatId.add(seat.getId());
            }
        }
        List<Seat> availableSeats=new ArrayList<>();
        for(Seat seat:allSeats){
            if(!unavailableSeatId.contains(seat.getId())){
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    public Booking bookTables(String email, BookingRequest bookingRequest) {
        User user = userRepository.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        List<Seat> availableSeats=getOnlyAvailableSeats(bookingRequest.getStartTime(),bookingRequest.getEndTime());
        if (availableSeats.size()<bookingRequest.getRequiredSeats()) {
            throw new ResourceNotFoundException("No available tables meet the required seat count.");
        }
        availableSeats.sort((Seat s1,Seat s2)-> s2.getTable().getCapacity()-s1.getTable().getCapacity());
        Booking booking = new Booking();
        booking.setSeats(bookingRequest.getRequiredSeats());
        List<Seat> seatsBooking=new ArrayList<>();
        Map<Integer,Integer> tableIdWithSeats=new HashMap<>();
        for(int ind=1;ind<=bookingRequest.getRequiredSeats();ind++){
            seatsBooking.add(availableSeats.get(ind-1));
        }
        booking.setSeatsBooked(seatsBooking);
        booking.setUser(user);
        booking.setPhoneNumber(bookingRequest.getPhoneNumber());
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());
        booking.setStatus(BookingStatus.BOOKED);
        booking.setAmountPaid(bookingRequest.getAmountPaid());
        booking.setTotalAmount(bookingRequest.getTotalPrice());
        if(bookingRequest.getAmountPaid()<bookingRequest.getTotalPrice()) booking.setPaymentStatus(PaymentStatus.PARTIAL_PAID);
        else if(bookingRequest.getAmountPaid()==bookingRequest.getTotalPrice()) booking.setPaymentStatus(PaymentStatus.TOTAL_PAID);
        else booking.setPaymentStatus(PaymentStatus.UNPAID);
        return bookingRepository.save(booking);
    }

    public List<Booking> getFutureBookingsByUserEmail(String userEmail) {
        return bookingRepository.findFutureBookingsByUserEmail(userEmail,BookingStatus.BOOKED);
    }

    public List<Booking> getAllBookingByUserEmail(String userEmail) {
        List<Booking> bookings= bookingRepository.findAllBookingByUserEmail(userEmail);
        for(Booking booking:bookings){
            if(booking.getEndTime()>Instant.now().getEpochSecond()){
                booking.setStatus(BookingStatus.NOT_COMPLETED);
            }
        }
        return  bookings;
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

        if (booking.getStatus()!=BookingStatus.BOOKED) {
            throw new IllegalStateException("Only booked bookings can be cancelled.");
        }

        long currentTime = Instant.now().getEpochSecond();
        long timeDifference = booking.getStartTime() - currentTime;

        if(booking.getEndTime()<currentTime) throw new RuntimeException("Booking cannot be cancelled, ending time is already passed, Contact restaurant owner.");
        //if cancel time is more than 1 hour then only we will process refund
        //TODO:Need to implement refund process
        if (timeDifference > (60*60)) {
            processRefund(booking);
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    private void freeUpTablesFromBooking(Booking booking) {
        booking.getSeatsBooked().clear();
    }

    public void completeBooking(int bookingId) throws IllegalStateException,ResourceNotFoundException{
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getStatus()!=BookingStatus.ACTIVE) {
            throw new IllegalStateException("Only Active bookings can be completed.");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
    }


    public void startBooking(int bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        if (booking.getStatus()!=BookingStatus.BOOKED) {
            throw new IllegalStateException("Invalid booking");
        }
        booking.setStatus(BookingStatus.ACTIVE);
        bookingRepository.save(booking);
    }

    public List<Booking> getUserBooking(User user){
        return bookingRepository.getByUser(user);
    }

    public List<Booking> getAllUpcomingBookedBookings(long startTime){
        return bookingRepository.getAllUpcomingBooking(startTime,BookingStatus.BOOKED);
    }

    public List<Booking> getAllUpcomingCancelledBookings(long startTime){
        return bookingRepository.getAllUpcomingBooking(startTime,BookingStatus.CANCELLED);
    }

    public List<Booking> getBookingByStatus(BookingStatus status){
        return bookingRepository.findByStatus(status);
    }

    public void deleteBooking(int bookingId){
        bookingRepository.deleteById(bookingId);
    }
}
