package com.lucasnvs.siboon.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.data.repository.UserRepository;
import com.lucasnvs.siboon.databinding.FragmentProfileBinding;
import com.lucasnvs.siboon.model.SessionManager;
import com.lucasnvs.siboon.utils.Constants;
import com.squareup.picasso.Picasso;

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
                //noinspection unchecked
                return (T) new ProfileViewModel(userRepository);
            }
        }).get(ProfileViewModel.class);

        setToolbar();

        profileViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.tvProfileName.setText(user.getName());
            binding.tvProfileEmail.setText(user.getEmail());

            Picasso.get().load(Constants.BASE_URL + user.getImg())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(binding.imgProfileAvatar);
        });



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

    private void setToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Perfil");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
