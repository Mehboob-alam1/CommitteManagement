package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.databinding.ActivityMainBinding;
import com.mehboob.committemanagement.fragments.CompletedEventsFragment;
import com.mehboob.committemanagement.fragments.UpcomingEventsFragment;
import com.mehboob.committemanagement.fragments.adapter.MyAdapter;
import com.mehboob.committemanagement.members.MyCommitteeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AuthViewModel authViewModel;
    private  TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        setContentView(binding.getRoot());

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.btnViewPager);

        binding.cardCommitte.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, ComitteesActivity.class));

        });






        tabLayout.setupWithViewPager(viewPager);

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        myAdapter.addFragment(new UpcomingEventsFragment() ,"Upcoming Events");
        myAdapter.addFragment(new CompletedEventsFragment() ,"Completed Events");


        viewPager.setAdapter(myAdapter);




    }

    @Override
    protected void onResume() {
        super.onResume();

        authViewModel.getCurrentUser().observe(this, firebaseUser -> {


            binding.txtEmail.setText(firebaseUser.getEmail());

        });
    }
}