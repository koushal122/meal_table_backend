package com.MealTable.meal_table.helper;

import com.MealTable.meal_table.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithQuantity{
    Product product;
    int quantity;
}
