package com.example.foodorderingapplication555;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapplication555.R;

import java.util.ArrayList;
import java.util.List;

public class CartForShop1 extends AppCompatActivity {

    private static final String TAG = "CartForShop1";
    private List<String> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartforshop1);

        // Retrieve cart items from CartManagerForShop1 singleton
        cartItems = CartManagerForShop1.getInstance().getCartItems();
        Log.d(TAG, "Cart items on create: " + cartItems);

        // Initialize layout and display items in cart
        LinearLayout cartLayout = findViewById(R.id.cartLayout);
        displayCartItems(cartLayout);

        // Confirm Order button functionality
        Button confirmOrderButton = findViewById(R.id.button10);
        confirmOrderButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(CartForShop1.this, "Select any item from the menu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CartForShop1.this, Shop1Menus.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(CartForShop1.this, OrderConfirmForShop1.class);
                intent.putStringArrayListExtra("cartItems", new ArrayList<>(cartItems));
                startActivity(intent);
            }
        });

        // Set up Delete button to clear the cart
        ImageView deleteButton = findViewById(R.id.imageView13);
        deleteButton.setOnClickListener(v -> {
            CartManagerForShop1.getInstance().clearCartItems();
            cartItems.clear();
            cartLayout.removeAllViews();
            Log.d(TAG, "Cart cleared.");
        });

        // New: Go To Menu Page button functionality
        Button goToMenuButton = findViewById(R.id.button9);
        goToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartForShop1.this, Shop1Menus.class);
            startActivity(intent);
        });
    }

    // Method to display cart items
    private void displayCartItems(LinearLayout cartLayout) {
        int serialNumber = 1;
        for (String item : cartItems) {
            TextView itemTextView = new TextView(this);
            itemTextView.setText(serialNumber + ". " + item);
            cartLayout.addView(itemTextView);
            serialNumber++;
        }
    }
}
