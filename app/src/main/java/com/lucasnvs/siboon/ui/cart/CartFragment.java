package com.lucasnvs.siboon.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.databinding.FragmentCartBinding;
import com.lucasnvs.siboon.ui.home.HomeViewModel;
import com.lucasnvs.siboon.ui.home.ProductAdapter;
import com.lucasnvs.siboon.ui.home.SectionAdapter;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    private CartViewModel cartViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProductRepository productRepository = new ProductRepository(requireContext());
        cartViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                //noinspection unchecked
                return (T) new CartViewModel(productRepository);
            }
        }).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ProductAdapter productAdapter = new ProductAdapter(getContext(), new ArrayList<>());
        binding.recyclerViewCart.setAdapter(productAdapter);

        cartViewModel.cart.observe(getViewLifecycleOwner(), products -> {
            productAdapter.setProducts(products);
            binding.recyclerViewCart.setAdapter(productAdapter);
        });

        cartViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e("CartFragment", "Erro recebido: " + error);
                Toast.makeText(getContext(), "Erro: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        cartViewModel.loadCart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}