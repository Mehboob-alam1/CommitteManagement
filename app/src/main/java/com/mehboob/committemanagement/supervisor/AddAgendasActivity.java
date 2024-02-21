package com.mehboob.committemanagement.supervisor;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Toast;

import com.mehboob.committemanagement.common.models.Agenda;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.databinding.ActivityAddAgendasBinding;

import java.util.ArrayList;
import java.util.List;


public class AddAgendasActivity extends AppCompatActivity {

    private ActivityAddAgendasBinding binding;

    private List<Uri> selectedImages = new ArrayList<>();
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImage;
    private String committeeName;
    private AuthViewModel authViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAgendasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

//        getImagesToViews();

        committeeName= getIntent().getStringExtra("comname");


        binding.btnDone.setOnClickListener(v -> {


            if (selectedImage!=null && !binding.etCommitteeAgenda.getText().toString().isEmpty()){
                String agenda= binding.etCommitteeAgenda.getText().toString();
                uploadData(agenda,selectedImage,committeeName);
                binding.progress.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(this, "Add all data", Toast.LENGTH_SHORT).show();
            }

        });

        binding.imgPick1.setOnClickListener(view -> openGallery());



    }

    private void uploadData(String agenda, Uri selectedImage,String committeeName) {


        authViewModel.uploadCommitteeAgendaImage(agenda, selectedImage, committeeName);

        authViewModel.getIfUpload().observe(this,aBoolean -> {
            binding.progress.setVisibility(View.GONE);

            if (aBoolean){

                Toast.makeText(this, "Data added", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

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
                selectedImage=selectedImageUri;
                binding.imgPick1.setImageURI(selectedImageUri);
            }
        }
    }
}
