package com.example.chatapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    ImageView profileImage;
    EditText nameInput;
    RadioGroup genderGroup;
    Button saveBtn;

    SharedPreferences prefs;
    private static final int PICK_IMAGE = 101;
    private static final int REQ_PERMISSION = 102;
    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profileImage);
        nameInput = findViewById(R.id.nameInput);
        genderGroup = findViewById(R.id.genderGroup);
        saveBtn = findViewById(R.id.saveButton);

        prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);

        // 🔐 Ask permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQ_PERMISSION);
                return;
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQ_PERMISSION);
                return;
            }
        }

        // 🧠 Load saved values
        nameInput.setText(prefs.getString("name", ""));
        String gender = prefs.getString("gender", "Male");
        if (gender.equals("Male")) genderGroup.check(R.id.radioMale);
        else genderGroup.check(R.id.radioFemale);

        String imageUriStr = prefs.getString("imageUri", null);
        if (imageUriStr != null) {
            selectedImageUri = Uri.parse(imageUriStr);
            loadImageFromUri(selectedImageUri);
        }

        profileImage.setOnClickListener(v -> {
            Intent pick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pick, PICK_IMAGE);
        });

        saveBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            int selectedId = genderGroup.getCheckedRadioButtonId();
            String genderValue = selectedId == R.id.radioMale ? "Male" : "Female";

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("name", name);
            editor.putString("gender", genderValue);
            if (selectedImageUri != null) editor.putString("imageUri", selectedImageUri.toString());
            editor.apply();

            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            loadImageFromUri(selectedImageUri);
        }
    }

    private void loadImageFromUri(Uri uri) {
        try {
            ImageDecoder.Source src = ImageDecoder.createSource(getContentResolver(), uri);
            Drawable drawable = ImageDecoder.decodeDrawable(src);
            profileImage.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Image load error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(reqCode, permissions, grantResults);
        if (reqCode == REQ_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate(); // restart now that permission is granted
        } else {
            Toast.makeText(this, "Permission denied. Can't load images.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
