package com.CleverTapTest.clevertapandroid;

import android.app.NotificationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.createNotificationChannel(getApplicationContext(), "YourChannelId", "Your Channel Name", "Your Channel Description", NotificationManager.IMPORTANCE_MAX, true);

        EditText nameEditText = findViewById(R.id.editTextTextName);
        EditText emailEditText = findViewById(R.id.editTextTextEmail);

        Button sendButton = findViewById(R.id.buttonSend);
        Button viewProductButton = findViewById(R.id.buttonViewProduct);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("sendButton", "sendButton clicked");
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                if (name.isEmpty() || email.isEmpty()) {
                    Snackbar.make(view, "Please Enter name and email", BaseTransientBottomBar.LENGTH_SHORT).show();
                } else {
                    Log.d("sendButton", "name:" + name + ",email:" + email);
                    nameEditText.setText("");
                    emailEditText.setText("");
                    HashMap<String, Object> userProfile = new HashMap<>();
                    userProfile.put("Name", name);
                    userProfile.put("Email", email);
                    assert clevertapDefaultInstance != null;
                    clevertapDefaultInstance.pushProfile(userProfile);
                }

            }
        });

        viewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("viewProductButton", "viewProductButton clicked");
                // event with properties
                HashMap<String, Object> prodViewedAction = new HashMap<>();
                prodViewedAction.put("Product ID", 1);
                prodViewedAction.put("Product Image", "https://d35fo82fjcw0y8.cloudfront.net/2018/07/26020307/customer-success-clevertap.jpg");
                prodViewedAction.put("Product Name", "CleverTap");

                assert clevertapDefaultInstance != null;
                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);
                Log.d("viewProductButton", "Product Viewed Event Raised with properties:" + prodViewedAction);
            }
        });
    }
}