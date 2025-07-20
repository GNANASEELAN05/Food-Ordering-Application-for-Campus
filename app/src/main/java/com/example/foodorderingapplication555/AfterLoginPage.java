package com.example.foodorderingapplication555;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderingapplication555.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AfterLoginPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private String userName;
    private ArrayList<String> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterloginpage);

        auth = FirebaseAuth.getInstance();

        // Initialize GoogleSignInClient with options to force account chooser on each logout
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        TextView textViewUsername = findViewById(R.id.textViewUsername);

        // Attempt to get the username from the intent
        userName = getIntent().getStringExtra("userName");

        if (userName != null) {
            // Display the username from the intent and save it to SharedPreferences
            textViewUsername.setText("Welcome, " + userName);
            saveUserNameToPreferences(userName);
        } else {
            // If username is not available in the intent, retrieve it from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            userName = sharedPreferences.getString("userName", null);
            if (userName != null) {
                textViewUsername.setText("Welcome, " + userName);
            } else {
                textViewUsername.setText("Welcome");  // Fallback message if username is still unavailable
            }
        }

        ImageView logoutButton = findViewById(R.id.imageView3);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        Button buttonNalabagamMenus = findViewById(R.id.button5);
        buttonNalabagamMenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLoginPage.this, Shop1Menus.class);
                startActivity(intent);
            }
        });

        Button buttonSodexoMenus = findViewById(R.id.button6);
        buttonSodexoMenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterLoginPage.this, Shop2Menus.class);
                startActivity(intent);
            }
        });
    }

    private void saveUserNameToPreferences(String userName) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", userName);
        editor.apply();
    }

    private void signOut() {
        auth.signOut();
        googleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Toast.makeText(AfterLoginPage.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
            clearUserNameFromPreferences();
            Intent intent = new Intent(AfterLoginPage.this, UserLoginPage.class);
            startActivity(intent);
            finish();
        });
    }

    private void clearUserNameFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("userName");
        editor.apply();
    }
}
