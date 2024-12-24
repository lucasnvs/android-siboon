package com.lucasnvs.siboon.ui.auth;

import static com.lucasnvs.siboon.utils.Constants.REGEX_VALID_PASSWORD;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
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

import java.sql.Array;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String name = String.valueOf(binding.editTextName.getText());
            String lastName = String.valueOf(binding.editTextLastName.getText());
            String email = String.valueOf(binding.editTextEmail.getText());
            String password = String.valueOf(binding.editTextPasswordSignup.getText());

            if (validateForm(name, lastName, email, password)) {
                signupViewModel.signup(name, lastName, email, password);
            }
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


    private Boolean validateForm(String name, String lastName, String email, String password) {

        String[] fields = new String[]{name, lastName, email, password};
        for (String field : fields) {
            if (field.isEmpty()) {
                Toast.makeText(context, "Todos os campos devem ser preenchidos.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "O E-mail inserido é inválido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Pattern.compile(REGEX_VALID_PASSWORD).matcher(password).matches()) {
            Toast.makeText(context, "A senha deve ter no mínimo 8 caracteres, contendo pelo menos um caractere especial e um número.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewLoginLink.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_login);
        });
    }
}
