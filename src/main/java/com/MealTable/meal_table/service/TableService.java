package com.MealTable.meal_table.service;

import com.MealTable.meal_table.model.TableEntity;
import com.MealTable.meal_table.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TableService {
    @Autowired
    TableRepository tableRepository;

    public TableEntity createTable(int capacity){
        try {
            return tableRepository.save(new TableEntity(capacity,capacity));
        }catch (Exception e){
            throw new RuntimeException("Unable to create table");
        }
    }

    public void deleteTable(int tableId){
        try {
            tableRepository.deleteById(tableId);
        }catch (Exception e){
            throw new RuntimeException("Unable to delete table with id "+tableId);
        }
    }

    public void bookSeatsInTable(int id,int seats){
        TableEntity table=tableRepository.findById(id).orElseThrow(()-> new RuntimeException("Table not found"));
        int availableSeats=table.getAvailableSeats();
        if(availableSeats<seats) throw new RuntimeException("Required number of seats is not available");
        tableRepository.updateTableById(availableSeats-seats,id);
    }

    public void freeUpTableSeat(int id,int seat){
        TableEntity table=tableRepository.findById(id).orElseThrow(()-> new RuntimeException("Table not found"));
        int capacityOfTable=table.getCapacity();
        if(capacityOfTable<seat) throw new RuntimeException("Invalid number of seats to free up for capacity "+capacityOfTable+" got "+seat);
        int availableSeat=table.getAvailableSeats();
        tableRepository.updateTableById(availableSeat+seat,id);
    }

}
