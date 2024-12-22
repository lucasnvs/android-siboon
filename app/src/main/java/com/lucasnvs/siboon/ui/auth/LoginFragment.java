package com.lucasnvs.siboon.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;
    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setContext(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        loginViewModel.loginResponse.observe(getViewLifecycleOwner(), loginResponse -> {
            if (loginResponse != null) {
                redirectToProfile();
            }
        });

        loginViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(context, "Erro: "+error, Toast.LENGTH_LONG).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(v -> {
            String loginAddress = String.valueOf(binding.editTextEmailCpf.getText());
            String password = String.valueOf(binding.editTextPassword.getText());
            loginViewModel.login(loginAddress, password);
        });

        binding.textViewSignupLink.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_signup);
        });
    }

    private void redirectToProfile() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.navigation_profile);
    }
}
