package com.MealTable.meal_table.service;


import com.MealTable.meal_table.exceptions.OrderNotFound;
import com.MealTable.meal_table.exceptions.ResourceNotFoundException;
import com.MealTable.meal_table.helper.ProductWithQuantity;
import com.MealTable.meal_table.helper.TimeHelper;
import com.MealTable.meal_table.model.*;
import com.MealTable.meal_table.payloads.CreateOrderRequest;
import com.MealTable.meal_table.repository.AddressRepository;
import com.MealTable.meal_table.repository.OrderProductQuantityRepository;
import com.MealTable.meal_table.repository.OrderRepository;
import com.MealTable.meal_table.util.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderProductQuantityRepository orderProductQuantityRepository;

    @Transactional
    public Order createOrder(User user, CreateOrderRequest createOrderRequest){
        Order order=new Order();
        Address address=addressRepository.findById(createOrderRequest.getAddressId()).orElseThrow(()->new ResourceNotFoundException("Address Not Found"));
        order.setAddress(address);
        order.setTotalPrice(createOrderRequest.getTotalPrice());
        order.setDeliveryNotes(createOrderRequest.getDeliveryNotes());
        order.setRequestedTime(TimeHelper.getCurrentEpochTime());
        order.setUser(user);
        order.setOrderStatus(OrderStatus.BOOKING_IN_PROGRESS);
        List<OrderProductQuantity> orderProductQuantities=new ArrayList<>();
        for(ProductWithQuantity productWithQuantity:createOrderRequest.getProductWithQuantities()){
            OrderProductQuantity orderProductQuantity=new OrderProductQuantity();
            orderProductQuantity.setProduct(productWithQuantity.getProduct());
            orderProductQuantity.setQuantity(productWithQuantity.getQuantity());
            orderProductQuantity.setOrder(order);
            orderProductQuantities.add(orderProductQuantity);
        }
        order.setOrderProductQuantityList(orderProductQuantities);
        order.setOrderStatus(OrderStatus.BOOKED);
        deleteCartForUser(user);
        return orderRepository.save(order);
    }

    public Order confirmOrder(long orderId){
        Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        order.setOrderConfirmed(TimeHelper.getCurrentEpochTime());
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return order;
    }

    public void deleteCartForUser(User user){
        Cart cart=cartService.getCartByUser(user);
        cartService.deleteCart(cart);
    }

    public void changeOrderStatus(Order order,OrderStatus status){
        order.setOrderStatus(status);
        orderRepository.save(order);
    }


    @Transactional
    public void deleteOrder(long orderId){
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            for(OrderProductQuantity orderProductQuantity:order.getOrderProductQuantityList()){
                orderProductQuantityRepository.deleteById(orderProductQuantity.getId());
            }
            orderRepository.delete(order);
            orderRepository.flush();
        } else {
            throw new RuntimeException("Order not found with id " + orderId);
        }
    }

    public List<Order> getAllOrderForUser(User user){
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public List<Order> getAllBookedOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.BOOKED);
    }

    public List<Order> getAllDeliveredOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.DELIVERED);
    }

    public List<Order> getAllDispatchedOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.DISPATCHED);
    }

    public List<Order> getAllReturnRequestOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.RETURN_REQUESTED);
    }

    public List<Order> getAllConfirmedOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.CONFIRMED);
    }


    public List<Order> getAllOutForReturnPickUpOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.OUT_FOR_RETURN_PICKUP);
    }

    public List<Order> getAllReturnedOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.RETURNED);
    }

    public List<Order> getAllCancelRequestedOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.CANCEL_REQUESTED);
    }

    public List<Order> getAllCancelRejectedOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.CANCEL_REJECTED);
    }

    public List<Order> getAllCancelledOrder(){
        return orderRepository.findByOrderStatus(OrderStatus.CANCELLED);
    }

    public List<Order> getAllReturnRejectedOrder() {
        return orderRepository.findByOrderStatus(OrderStatus.RETURN_REJECTED);
    }

    public void cancelOrderRequest(long orderId) throws OrderNotFound {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.CANCEL_REQUESTED);
        order.setCancelRequested(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void approveCancelOrder(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setCancelApproved(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void rejectCancelOrder(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.CANCEL_REJECTED);
        order.setCancelRejected(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void dispatchOrder(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.DISPATCHED);
        order.setOrderDispatched(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void deliverOrder(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDelivered(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void returnOrderRequest(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.RETURN_REQUESTED);
        order.setReturnRequested(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void rejectReturnOrder(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.RETURN_REJECTED);
        order.setReturnRejected(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void outForReturnPickUp(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.OUT_FOR_RETURN_PICKUP);
        order.setOutForPickUp(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }

    public void orderReturned(long orderId) throws OrderNotFound{
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotFound("Order not found"));
        order.setOrderStatus(OrderStatus.RETURNED);
        order.setReturned(TimeHelper.getCurrentEpochTime());
        orderRepository.save(order);
    }
}
