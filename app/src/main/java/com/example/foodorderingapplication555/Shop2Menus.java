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

public class Shop2Menus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop2menus);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Next button to navigate to Shop2Menus2Page
        Button nextButton = findViewById(R.id.button777);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(Shop2Menus.this, Shop2Menus2Page.class);
            startActivity(intent);
        });

        // Back button to navigate to AfterLoginPage
        Button backButton = findViewById(R.id.button2121);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Shop2Menus.this, AfterLoginPage.class);
            startActivity(intent);
            finish();
        });

        // Cart ImageView to navigate to CartForShop2
        ImageView cartImageView = findViewById(R.id.imageView555);
        cartImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Shop2Menus.this, CartForShop2.class);
            startActivity(intent);
        });

        // Add to Cart buttons with Toast message
        findViewById(R.id.button1111).setOnClickListener(v -> addToCart("Chapatti Set - ₹50.00"));
        findViewById(R.id.button1116).setOnClickListener(v -> addToCart("Pepper Chicken - ₹130.00"));
        findViewById(R.id.button1115).setOnClickListener(v -> addToCart("Noodles - ₹110.00"));
        findViewById(R.id.button1112).setOnClickListener(v -> addToCart("Parotta Set - ₹40.00"));
        findViewById(R.id.button1114).setOnClickListener(v -> addToCart("Chicken Rice - ₹100.00"));
        findViewById(R.id.button1113).setOnClickListener(v -> addToCart("Biryani - ₹140.00"));
    }

    private void addToCart(String item) {
        CartManagerForShop2.getInstance().addItemToCart(item);
        Toast.makeText(this, item + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
