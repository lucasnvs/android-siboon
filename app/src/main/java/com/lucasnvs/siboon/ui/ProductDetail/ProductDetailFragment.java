package com.lucasnvs.siboon.ui.ProductDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.databinding.FragmentProductDetailBinding;
import com.lucasnvs.siboon.utils.Constants;
import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private ProductDetailViewModel productDetailViewModel;
    public static String ARG_PRODUCT_ID = "PRODUCT_ID_ARG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProductRepository productRepository = new ProductRepository(requireContext());
        productDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ProductDetailViewModel(productRepository);
            }
        }).get(ProductDetailViewModel.class);

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        productDetailViewModel.errorLiveData.observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Erro: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        productDetailViewModel.product.observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                updateUI(product);
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            long productId = args.getLong(ARG_PRODUCT_ID, -1);
            if (productId != -1) {
                productDetailViewModel.loadProduct(productId);
            }
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void updateUI(@NonNull com.lucasnvs.siboon.model.Product product) {
        Picasso.get().load(Constants.BASE_URL + product.getImageUrl()).into(binding.productImageView);

        binding.productNameTextView.setText(product.getTitle());

        binding.productPriceTextView.setText(String.format("R$ %.2f", product.getPrice()));

        binding.productInstallmentTextView.setText(
                String.format("ou até %dx de R$ %.2f", product.getInstallments(), product.getPrice() / product.getInstallments())
        );

        binding.productDescriptionTextView.setText(product.getDescription());
    }
}