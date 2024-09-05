package com.MealTable.meal_table.service;

import com.MealTable.meal_table.controller.AuthController;
import com.MealTable.meal_table.exceptions.ResourceNotFoundException;
import com.MealTable.meal_table.model.Product;
import com.MealTable.meal_table.repository.ProductRepository;
import com.mysql.cj.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ProductRepository productRepository;
    private static final String IMAGE_DIR = "images/";

    public Product saveProduct(Product product, MultipartFile imageFile) throws Exception {
        if (productRepository.existsByName(product.getName())) {
            throw new RuntimeException("Product name must be unique");
        }
        product.setImagePath(saveImage(product.getName(),imageFile));
        return productRepository.save(product);
    }

    private String saveImage(String name, MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new RuntimeException("Image file is empty");
        }
        Path directoryPath = Paths.get(IMAGE_DIR).toAbsolutePath().normalize();
        Files.createDirectories(directoryPath);

        String fileName = name+".jpg";
        logger.info("saving with filename {}", fileName);
        Path imagePath = directoryPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), imagePath);
        logger.info("file path is {}", imagePath);
        return IMAGE_DIR+fileName;
    }

    public byte[] getImage(String imagePath) throws IOException {
        return Files.readAllBytes(Paths.get(imagePath));
    }

    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }
}
