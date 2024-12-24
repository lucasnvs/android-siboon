package com.lucasnvs.siboon.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.databinding.FragmentCartBinding;
import com.lucasnvs.siboon.utils.SpacesItemDecoration;

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

        setToolbar();

        binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(20);
        binding.rvCart.addItemDecoration(itemDecoration);

        CartAdapter cartAdapter = new CartAdapter(getContext(), new ArrayList<>());
        binding.rvCart.setAdapter(cartAdapter);

        // The Decrease and the Increase buttons cause a bug: when clicked, the fragment reloads all the items in the cart.
        cartViewModel.cart.observe(getViewLifecycleOwner(), cartList -> {
            Boolean isEmpty = cartList != null && cartList.isEmpty();

            binding.tvEmptyCart.setVisibility(Boolean.TRUE.equals(isEmpty) ? View.VISIBLE : View.GONE);
            binding.layoutCart.setVisibility(Boolean.TRUE.equals(isEmpty) ? View.GONE : View.VISIBLE);

            if(!isEmpty) {
                cartAdapter.setProducts(cartList);
                binding.rvCart.setAdapter(cartAdapter);
            }
        });


        cartViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e("CartFragment", "Erro recebido: " + error);
                Toast.makeText(getContext(), "Erro: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void setToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Carrinho");
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