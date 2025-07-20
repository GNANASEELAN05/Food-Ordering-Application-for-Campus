package com.example.foodorderingapplication555;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CartManagerForShop2 {
    private static final String TAG = "CartManagerForShop2";
    private static CartManagerForShop2 instance;
    private final List<String> cartItems;

    private CartManagerForShop2() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManagerForShop2 getInstance() {
        if (instance == null) {
            instance = new CartManagerForShop2();
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
