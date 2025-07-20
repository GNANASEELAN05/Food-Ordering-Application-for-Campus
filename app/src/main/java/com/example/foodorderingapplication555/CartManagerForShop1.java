package com.example.foodorderingapplication555;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CartManagerForShop1 {
    private static final String TAG = "CartManagerForShop1";
    private static CartManagerForShop1 instance;
    private final List<String> cartItems;

    private CartManagerForShop1() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManagerForShop1 getInstance() {
        if (instance == null) {
            instance = new CartManagerForShop1();
        }
        return instance;
    }

    public void addItemToCart(String item) {
        cartItems.add(item);
        Log.d(TAG, "Item added to cart: " + item + ". Current cart: " + cartItems);
    }

    public List<String> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public void clearCartItems() {
        cartItems.clear();
        Log.d(TAG, "Cart cleared. Current cart: " + cartItems);
    }
}
