package com.MealTable.meal_table.controller;

import com.MealTable.meal_table.model.Product;
import com.MealTable.meal_table.payloads.AddProductRequest;
import com.MealTable.meal_table.service.ProductService;
import com.MealTable.meal_table.service.UserService;
import com.MealTable.meal_table.util.MealType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/add-product",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addProduct(HttpServletRequest request,@ModelAttribute AddProductRequest addProductRequest) {
        Product savedProduct = null;
        try {
            userService.isAdminRequest(request);
            savedProduct = productService.saveProduct(new Product(addProductRequest.getType(), addProductRequest.getName(), addProductRequest.getDescription(), addProductRequest.getPrice()), addProductRequest.getImage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Product>> getProductsByType(@PathVariable String type) {
        List<Product> products = productService.getProductsByType(type);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/get-all-products")
    public ResponseEntity<List<Product>> getAllProducts() throws MalformedURLException {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-tags")
    public List<String> getAllTags(){
        List<String> tags=new ArrayList<>();
        for(MealType mealType:MealType.values()){
            tags.add(mealType.toString().replaceAll("_"," "));
        }
        return tags;
    }

    @PutMapping(value = "/delete-product")
    public ResponseEntity<?> deleteProduct(HttpServletRequest request,@RequestParam int productId){
        try {
            userService.isAdminRequest(request);
            productService.deleteProduct(productId);
            return ResponseEntity.status(200).body("Product deleted Successfully");
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


}
