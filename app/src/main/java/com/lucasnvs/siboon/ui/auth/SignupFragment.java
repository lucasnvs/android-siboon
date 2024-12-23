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
import com.lucasnvs.siboon.databinding.FragmentSignupBinding;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding binding;
    private SignupViewModel signupViewModel;
    private Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        signupViewModel.setContext(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);

        binding.buttonSignup.setOnClickListener(v -> {
            signupViewModel.signup(
                    String.valueOf(binding.editTextName.getText()),
                    String.valueOf(binding.editTextLastName.getText()),
                    String.valueOf(binding.editTextEmail.getText()),
                    String.valueOf(binding.editTextPasswordSignup.getText())
            );
        });

        signupViewModel.loginResponse.observe(getViewLifecycleOwner(), loginResponse -> {
            if (loginResponse != null) {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.navigation_profile);
            }
        });

        signupViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            }
        });

        return binding.getRoot();
    }
}
