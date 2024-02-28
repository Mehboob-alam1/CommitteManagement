package com.mehboob.committemanagement.common.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;

import com.mehboob.committemanagement.R;
import com.mehboob.committemanagement.common.viewmodels.AuthViewModel;
import com.mehboob.committemanagement.databinding.ActivityMainBinding;
import com.mehboob.committemanagement.fragments.CompletedEventsFragment;
import com.mehboob.committemanagement.fragments.ProgressEventsFragment;
import com.mehboob.committemanagement.fragments.UpcomingEventsFragment;
import com.mehboob.committemanagement.fragments.adapter.MyAdapter;
import com.mehboob.committemanagement.members.MyCommitteeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AuthViewModel authViewModel;
    private  TabLayout tabLayout;

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        setContentView(binding.getRoot());

        tabLayout = findViewById(R.id.tablayout);
        frameLayout = findViewById(R.id.framlayout);

        binding.cardCommitte.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, ComitteesActivity.class));

        });

        tabLayout.addTab(tabLayout.newTab().setText("Progress"));
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Replace the fragment in the FrameLayout based on the selected tab
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framlayout, new ProgressEventsFragment())
                                .commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framlayout, new UpcomingEventsFragment())
                                .commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.framlayout, new CompletedEventsFragment())
                                .commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment[] fragments = new Fragment[]{
//                new ProgressEventsFragment(),
//                new UpcomingEventsFragment(),
//                new CompletedEventsFragment()
//        };
//        String[] tabTitles = new String[]{"Progress", "Upcoming", "Completed"};
//

//
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//        myAdapter.addFragment(new ProgressEventsFragment() ,"Progress");
//        myAdapter.addFragment(new CompletedEventsFragment() ,"Completed");
//        myAdapter.addFragment(new CompletedEventsFragment() ,"Completed ");
//
//
//        viewPager.setAdapter(myAdapter);




    }

    @Override
    protected void onResume() {
        super.onResume();

        authViewModel.getCurrentUser().observe(this, firebaseUser -> {


            binding.txtEmail.setText(firebaseUser.getEmail());

        });
    }
}