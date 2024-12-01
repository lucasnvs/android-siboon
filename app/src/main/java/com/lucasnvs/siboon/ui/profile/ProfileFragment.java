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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.data.repository.UserRepository;
import com.lucasnvs.siboon.databinding.FragmentProfileBinding;
import com.lucasnvs.siboon.model.SessionManager;
import com.lucasnvs.siboon.ui.home.HomeViewModel;

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

        UserRepository userRepository = new UserRepository(requireContext());
        ProfileViewModel profileViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProfileViewModel(userRepository);
            }
        }).get(ProfileViewModel.class);

        profileViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.tvProfileName.setText(user.getName());
            binding.tvProfileEmail.setText(user.getEmail());
        });

        if (!sessionManager.isLoggedIn()) {
            redirectToLogin(view);
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
