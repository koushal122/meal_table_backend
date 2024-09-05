package com.MealTable.meal_table.payloads;

import com.MealTable.meal_table.helper.ProductWithQuantity;
import com.MealTable.meal_table.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private double totalPrice;
    private long addressId;
    private String deliveryNotes;
    private List<ProductWithQuantity> productWithQuantities;
}


