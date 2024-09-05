package com.MealTable.meal_table.service;

import com.MealTable.meal_table.model.Tickets;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.payloads.CreateTicketRequest;
import com.MealTable.meal_table.repository.TicketRepository;
import com.MealTable.meal_table.util.TicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;
    public void createTicket(User user, CreateTicketRequest createTicketRequest){
        Tickets tickets=new Tickets();
        tickets.setUser(user);
        tickets.setMessage(createTicketRequest.getMessage());
        tickets.setSubject(createTicketRequest.getSubject());
        tickets.setPhoneNumber(createTicketRequest.getPhoneNumber());
        tickets.setTicketStatus(TicketStatus.OPEN);
        ticketRepository.save(tickets);
    }

    public void resolveTicket(long ticketId){
        Tickets tickets = ticketRepository.findById(ticketId).orElseThrow(()->new RuntimeException("Ticket not Found"));
        tickets.setTicketStatus(TicketStatus.RESOLVED);
        ticketRepository.save(tickets);
    }

    public List<Tickets> getAllOpenTickets(){
        return ticketRepository.findByTicketStatus(TicketStatus.OPEN);
    }

    public List<Tickets> getAllResolvedTickets(){
        return ticketRepository.findByTicketStatus(TicketStatus.RESOLVED);
    }

    public List<Tickets> getTicketForUser(User user){
        return ticketRepository.findByUser(user);
    }
}
