package com.lucasnvs.siboon.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.databinding.FragmentProfileBinding;
import com.lucasnvs.siboon.model.SessionManager;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.sessionManager = new SessionManager(requireContext());

        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getUserName().observe(getViewLifecycleOwner(), name -> binding.tvProfileName.setText(name));
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), email -> binding.tvProfileEmail.setText(email));

        if (!sessionManager.isLoggedIn()) {
            redirectToLogin(view);
        } else {
            loadUserData(profileViewModel);
        }

        setupListeners(profileViewModel);
    }

    private void setupListeners(ProfileViewModel profileViewModel) {
        binding.btnLogout.setOnClickListener(v -> logout());

        binding.tvOptionPersonalData.setOnClickListener(v -> {
        });

        binding.tvOptionAddress.setOnClickListener(v -> {
        });

        binding.tvOptionOrders.setOnClickListener(v -> {
        });

        binding.tvOptionSupport.setOnClickListener(v -> {
        });
    }

    private void loadUserData(ProfileViewModel profileViewModel) {
        profileViewModel.setUserName("João Ninguém");
        profileViewModel.setUserEmail("joao@email.com");
    }

    private void logout() {
        sessionManager.clear();
        redirectToLogin(requireView());
    }

    private void redirectToLogin(View view) {
        NavController navController = Navigation.findNavController(view);
        Log.d("ProfileFragment", "Navigating to login");
        navController.navigate(R.id.navigation_login);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
