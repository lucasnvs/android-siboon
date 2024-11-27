package com.lucasnvs.siboon.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getUserName().observe(getViewLifecycleOwner(), name -> binding.tvProfileName.setText(name));
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), email -> binding.tvProfileEmail.setText(email));

        if (!isUserLoggedIn()) {
            redirectToLogin(view);
        } else {
            loadUserData(profileViewModel);
        }

        setupListeners(profileViewModel);
    }

    private void setupListeners(ProfileViewModel profileViewModel) {
        binding.btnLogout.setOnClickListener(v -> {
            logout();
            redirectToLogin(requireView());
        });

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
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", true); // TODO: b: false
    }

    private void redirectToLogin(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.navigation_login);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
