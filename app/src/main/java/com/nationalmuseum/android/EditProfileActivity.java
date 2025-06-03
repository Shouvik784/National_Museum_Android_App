package com.nationalmuseum.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etPhone, etDob;
    Button btnSave;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    DocumentReference userDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etDob = findViewById(R.id.etDob);
        btnSave = findViewById(R.id.btnSave);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        userDocRef = db.collection("Users").document(uid); // Firestore path

        etFirstName.setText(getIntent().getStringExtra("firstName"));
        etLastName.setText(getIntent().getStringExtra("lastName"));
        etPhone.setText(getIntent().getStringExtra("phone"));
        etDob.setText(getIntent().getStringExtra("dob"));

        // Optional: Set existing data from Intent
        etFirstName.setText(getIntent().getStringExtra("firstName"));
        etLastName.setText(getIntent().getStringExtra("lastName"));
        etPhone.setText(getIntent().getStringExtra("phone"));
        etDob.setText(getIntent().getStringExtra("dob"));

        btnSave.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String dob = etDob.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || dob.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> updates = new HashMap<>();
            updates.put("firstName", firstName);
            updates.put("lastName", lastName);
            updates.put("phone", phone);
            updates.put("dob", dob);

            userDocRef.set(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to update Firestore", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}

