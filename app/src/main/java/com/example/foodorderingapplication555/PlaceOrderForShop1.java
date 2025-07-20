package com.example.foodorderingapplication555;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderingapplication555.R;

public class PlaceOrderForShop1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_placeorderforshop1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // "Place Another Order" button to navigate to Shop1Menus
        Button placeAnotherOrderButton = findViewById(R.id.button14);
        placeAnotherOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlaceOrderForShop1.this, Shop1Menus.class);
            startActivity(intent);
        });
    }
}
