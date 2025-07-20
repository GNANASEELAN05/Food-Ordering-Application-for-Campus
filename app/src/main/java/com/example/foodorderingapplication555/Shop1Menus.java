package com.example.foodorderingapplication555;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapplication555.R;

public class Shop1Menus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop1menus);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Next button to navigate to Shop1Menus2Page
        Button nextButton = findViewById(R.id.button7);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Shop1Menus.this, Shop1Menus2Page.class);
            startActivity(intent);
        });

        // Back button to navigate to AfterLoginPage
        Button backButton = findViewById(R.id.button21);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Shop1Menus.this, AfterLoginPage.class);
            startActivity(intent);
            finish();
        });

        // Cart ImageView to navigate to CartForShop1
        ImageView cartImageView = findViewById(R.id.imageView5);
        cartImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Shop1Menus.this, CartForShop1.class);
            startActivity(intent); // Navigates to CartForShop1
        });

        // Add to Cart buttons with Toast message
        findViewById(R.id.button111).setOnClickListener(v -> addToCart("Chapatti Set - ₹40.00"));
        findViewById(R.id.button116).setOnClickListener(v -> addToCart("Pepper Chicken - ₹120.00"));
        findViewById(R.id.button115).setOnClickListener(v -> addToCart("Noodles - ₹100.00"));
        findViewById(R.id.button112).setOnClickListener(v -> addToCart("Parotta Set - ₹20.00"));
        findViewById(R.id.button114).setOnClickListener(v -> addToCart("ChickenRice - ₹90.00"));
        findViewById(R.id.button113).setOnClickListener(v -> addToCart("Biryani - ₹130.00"));
    }

    private void addToCart(String item) {
        CartManagerForShop1.getInstance().addItemToCart(item);
        Toast.makeText(this, item + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
