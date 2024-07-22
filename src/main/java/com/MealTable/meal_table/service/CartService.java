package com.MealTable.meal_table.service;

import com.MealTable.meal_table.model.Cart;
import com.MealTable.meal_table.model.Product;
import com.MealTable.meal_table.model.User;
import com.MealTable.meal_table.repository.CartRepository;
import com.MealTable.meal_table.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElse(new Cart());
    }

    public Cart addProductToCart(User user, int productId, int quantity) {
        Cart cart = getCartByUser(user);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().add(product);
        cart.getProductQuantityWithId().put(productId, cart.getProductQuantityWithId().getOrDefault(productId, 0) + quantity);
        cart.setTotalPrice(calculateTotalPrice(cart));
        cart.setNumberOfItems(cart.getProducts().size());
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart updateProductQuantity(User user, int productId, int quantity) {
        Cart cart = getCartByUser(user);
        if (cart.getProductQuantityWithId().containsKey(productId)) {
            cart.getProductQuantityWithId().put(productId, quantity);
            cart.setTotalPrice(calculateTotalPrice(cart));
        }else throw new RuntimeException("Product is not found in cart of user "+user.getUserEmail());
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(User user, int productId) {
        Cart cart = getCartByUser(user);
        cart.getProducts().removeIf(product -> product.getId() == productId);
        cart.getProductQuantityWithId().remove(productId);
        cart.setTotalPrice(calculateTotalPrice(cart));
        cart.setNumberOfItems(cart.getProducts().size());
        return cartRepository.save(cart);
    }

    private double calculateTotalPrice(Cart cart) {
        return cart.getProducts().stream().mapToDouble(product -> {
            int quantity = cart.getProductQuantityWithId().get(product.getId());
            return product.getPrice() * quantity;
        }).sum();
    }

    public int getNumberOfItemsInCart(User user) {
        return getCartByUser(user).getNumberOfItems();
    }

    public List<Cart> getAllCartsByUser(User user) {
        return cartRepository.findAllByUser(user);
    }
}

