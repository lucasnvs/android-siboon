package com.lucasnvs.siboon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lucasnvs.siboon.databinding.ActivityMainBinding;
import com.lucasnvs.siboon.model.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.lucasnvs.siboon.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(navView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            setToolbarVisibility(destination.getId());

            if (destination.getId() == R.id.navigation_profile) {
                if (!new SessionManager(this.getApplicationContext()).isLoggedIn()) {
                    navController.navigate(R.id.navigation_login);
                }
            }
        });

        binding.navView.setOnItemSelectedListener(item -> {
            navController.popBackStack(R.id.navigation_home, false);
            return NavigationUI.onNavDestinationSelected(item, navController);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}