package com.lld.amazon.service;

import com.lld.amazon.entity.Product;
import com.lld.amazon.entity.ShoppingCart;
import com.lld.amazon.entity.ShoppingCartProduct;
import com.lld.amazon.entity.Users;
import com.lld.amazon.repository.ProductRepository;
import com.lld.amazon.repository.ShoppingCartRepository;
import com.lld.amazon.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsersRepository userRepository;

    public ShoppingCart addProductToCart(Long userId, Long productId, int quantity) {
        // Fetch the user
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Fetch the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        // Fetch or create the shopping cart for the user
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return shoppingCartRepository.save(newCart);
                });

        // Check if the product already exists in the cart
        ShoppingCartProduct existingCartProduct = shoppingCart.getProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartProduct != null) {
            // Update quantity if the product is already in the cart
            existingCartProduct.setQuantity(existingCartProduct.getQuantity() + quantity);
        } else {
            // Add new product to the cart
            ShoppingCartProduct cartProduct = new ShoppingCartProduct();
            cartProduct.setShoppingCart(shoppingCart);
            cartProduct.setProduct(product);
            cartProduct.setQuantity(quantity);
            shoppingCart.getProducts().add(cartProduct);
        }

        // Update total amount
        double totalAmount = shoppingCart.getProducts().stream()
                .mapToDouble(cartProduct -> cartProduct.getProduct().getPrice() * cartProduct.getQuantity())
                .sum();
        shoppingCart.setTotalAmount(totalAmount);

        return shoppingCartRepository.save(shoppingCart);
    }
}

