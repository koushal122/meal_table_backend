package com.MealTable.meal_table.repository;

import com.MealTable.meal_table.model.TableEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update TableEntity u set u.availableSeats = ?1 where u.id = ?2")
    void updateTableById(int newAvailableSeat,int id);
}