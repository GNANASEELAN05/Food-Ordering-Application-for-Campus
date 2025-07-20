package com.example.foodorderingapplication555;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.foodorderingapplication555.R;

import java.util.ArrayList;

public class OrderConfirmForShop1 extends AppCompatActivity {

    private static final String PHONE_NUMBER = "+919345218656"; // Replace with the desired phone number
    private static final int SMS_PERMISSION_REQUEST_CODE = 101;
    private static final String NOTIFICATION_CHANNEL_ID = "orderChannelShop1";

    private ArrayList<String> cartItems;
    private LinearLayout orderLayout;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconfirmforshop1);

        orderLayout = findViewById(R.id.orderLayout);

        // Retrieve userName from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "Customer");

        // Retrieve cart items passed from CartForShop1
        cartItems = getIntent().getStringArrayListExtra("cartItems");
        if (cartItems != null && !cartItems.isEmpty()) {
            displayOrderDetails(cartItems);
        } else {
            displayEmptyOrderMessage();
        }

        createNotificationChannel();

        // Home button to navigate back to AfterLoginPage
        ImageView homeButton = findViewById(R.id.imageView22);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderConfirmForShop1.this, AfterLoginPage.class);
            startActivity(intent);
        });

        // Delete all items button
        ImageView deleteButton = findViewById(R.id.imageView19);
        deleteButton.setOnClickListener(v -> deleteAllItems());

        // Place order button
        Button placeOrderButton = findViewById(R.id.button12);
        placeOrderButton.setOnClickListener(v -> {
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(OrderConfirmForShop1.this, "Select any item from the menu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderConfirmForShop1.this, Shop1Menus.class);
                startActivity(intent);
            } else {
                requestSmsPermissionAndSend();
            }
        });
    }

    private void displayOrderDetails(ArrayList<String> cartItems) {
        orderLayout.removeAllViews();

        int serialNumber = 1;
        double totalPrice = 0;

        for (String item : cartItems) {
            TextView itemTextView = new TextView(this);
            itemTextView.setText(serialNumber + ". " + item);
            orderLayout.addView(itemTextView);

            totalPrice += extractPrice(item);
            serialNumber++;
        }

        TextView totalTextView = new TextView(this);
        totalTextView.setText("Total: ₹" + totalPrice);
        orderLayout.addView(totalTextView);
    }

    private double extractPrice(String item) {
        try {
            String[] parts = item.split("- ₹");
            return parts.length > 1 ? Double.parseDouble(parts[1]) : 0;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void displayEmptyOrderMessage() {
        orderLayout.removeAllViews();

        TextView emptyTextView = new TextView(this);
        emptyTextView.setText("No items in the order.");
        orderLayout.addView(emptyTextView);

        TextView totalTextView = new TextView(this);
        totalTextView.setText("Total: ₹0.0");
        orderLayout.addView(totalTextView);
    }

    private void deleteAllItems() {
        cartItems.clear();
        displayEmptyOrderMessage();
    }

    private void requestSmsPermissionAndSend() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            sendOrderConfirmation();
        }
    }

    private void sendOrderConfirmation() {
        StringBuilder orderDetails = new StringBuilder();
        double totalPrice = 0;

        for (String item : cartItems) {
            orderDetails.append(item).append("\n");
            totalPrice += extractPrice(item);
        }
        orderDetails.append("Total: ₹").append(totalPrice);

        String message = userName + " has ordered the following items:\n" + orderDetails.toString();

        // Send SMS message
        sendSms(message);

        // Send Notification
        sendNotification(orderDetails.toString());

        // Navigate to PlaceOrderForShop1 page
        Intent intent = new Intent(OrderConfirmForShop1.this, PlaceOrderForShop1.class);
        startActivity(intent);
    }

    private void sendSms(String message) {
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            Log.d("SMS Permission Status", "Permission: " + (permissionCheck == PackageManager.PERMISSION_GRANTED ? "Granted" : "Denied"));

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();

                // Automatically divide the message into parts but send it as a single SMS
                ArrayList<String> messageParts = smsManager.divideMessage(message);

                // Use sendMultipartTextMessage to send all parts together
                smsManager.sendMultipartTextMessage(PHONE_NUMBER, null, messageParts, null, null);

                Toast.makeText(this, "Order placed and SMS sent successfully!", Toast.LENGTH_SHORT).show();
                Log.d("SMS Status", "SMS sent to " + PHONE_NUMBER);
            } else {
                Log.e("SMS Error", "SMS permission not granted.");
                Toast.makeText(this, "SMS permission not granted.", Toast.LENGTH_SHORT).show();
            }
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Invalid phone number format.", Toast.LENGTH_SHORT).show();
            Log.e("SMS Error", "Invalid phone number format", e);
        } catch (SecurityException e) {
            Toast.makeText(this, "SMS permission error.", Toast.LENGTH_SHORT).show();
            Log.e("SMS Error", "Security exception - check permissions", e);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_SHORT).show();
            Log.e("SMS Error", "Failed to send SMS", e);
        }
    }

    private void sendNotification(String orderDetails) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Order Confirmation")
                .setContentText("Your order has been placed successfully!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Items ordered:\n" + orderDetails))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Notification Channel for Shop1";
            String description = "Channel for order notifications for Shop1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOrderConfirmation();
            } else {
                Toast.makeText(this, "SMS permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
