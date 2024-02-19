package com.mehboob.committemanagement.supervisor;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.widget.Toast;

import com.mehboob.committemanagement.databinding.ActivityAddAgendasBinding;

import java.util.ArrayList;
import java.util.List;


public class AddAgendasActivity extends AppCompatActivity {

    private ActivityAddAgendasBinding binding;

    private List<Uri> selectedImages = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAgendasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getImagesToViews();


        binding.btnDone.setOnClickListener(v -> {

            Toast.makeText(this, "" + selectedImages.toString(), Toast.LENGTH_SHORT).show();
        });

        binding.imgPick1.setOnClickListener(view -> openGallery());

    }





    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Set the selected image to the ImageView
                binding.imgPick1.setImageURI(selectedImageUri);
            }
        }
    }
}
