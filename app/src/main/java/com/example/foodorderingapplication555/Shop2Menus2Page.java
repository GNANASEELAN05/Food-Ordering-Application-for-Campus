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

public class Shop2Menus2Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop2menus2page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Home button to navigate to AfterLoginPage
        ImageView homeButton = findViewById(R.id.imageView);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Shop2Menus2Page.this, AfterLoginPage.class);
            startActivity(intent);
            finish();
        });

        // Back button to navigate to Shop2Menus
        Button backButton = findViewById(R.id.button21);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Shop2Menus2Page.this, Shop2Menus.class);
            startActivity(intent);
            finish();
        });

        // Cart ImageView to navigate to CartForShop2
        ImageView cartImageView = findViewById(R.id.imageView18);
        cartImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Shop2Menus2Page.this, CartForShop2.class);
            startActivity(intent);
        });

        // Add to Cart buttons with Toast message
        findViewById(R.id.button121).setOnClickListener(v -> addToCart("Full Grill Chicken - ₹460.00"));
        findViewById(R.id.button117).setOnClickListener(v -> addToCart("Dosa Set - ₹40.00"));
        findViewById(R.id.button118).setOnClickListener(v -> addToCart("Roast Dosa - ₹50.00"));
        findViewById(R.id.button120).setOnClickListener(v -> addToCart("Curd Rice - ₹60.00"));
        findViewById(R.id.button119).setOnClickListener(v -> addToCart("Kothu Parotta - ₹80.00"));
    }

    private void addToCart(String item) {
        CartManagerForShop2.getInstance().addItemToCart(item);
        Toast.makeText(this, item + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
