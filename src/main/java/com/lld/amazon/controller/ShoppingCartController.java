package com.lld.amazon.controller;

import com.lld.amazon.entity.ShoppingCart;
import com.lld.amazon.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/{userId}/add-product/{productId}")
    public ShoppingCart addProductToCart(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") int quantity) {
        return shoppingCartService.addProductToCart(userId, productId, quantity);
    }

    

}

