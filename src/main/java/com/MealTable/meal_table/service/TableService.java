package com.MealTable.meal_table.service;

import com.MealTable.meal_table.model.Seat;
import com.MealTable.meal_table.model.TableEntity;
import com.MealTable.meal_table.repository.SeatRepository;
import com.MealTable.meal_table.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {
    @Autowired
    TableRepository tableRepository;
    @Autowired
    SeatRepository seatRepository;

    public TableEntity createTable(int capacity){
        try {
            List<Seat> seats=new ArrayList<>();
            TableEntity table=new TableEntity();
            table.setCapacity(capacity);
            tableRepository.save(table);
            for(int cap=0;cap<capacity;cap++){
                Seat newSeat=new Seat();
                newSeat.setTable(table);
                newSeat.setTabId(table.getId());
                seats.add(newSeat);
                seatRepository.save(newSeat);
            }
            table.setSeats(seats);
            return tableRepository.save(table);
        }catch (Exception e){
            throw new RuntimeException("Unable to create table");
        }
    }

    public void deleteTable(int tableId){
        try {
            TableEntity table=tableRepository.findById(tableId).orElseThrow(()->new RuntimeException("No table found"));
            for(Seat seat:table.getSeats()){
                seatRepository.deleteById(seat.getId());
            }
            tableRepository.deleteById(tableId);
        }catch (Exception e){
            throw new RuntimeException("Unable to delete table with id "+tableId);
        }
    }

    public List<TableEntity> getAllTables(){
        return tableRepository.findAll();
    }

}
