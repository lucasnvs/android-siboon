package com.lucasnvs.siboon.ui.productdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.databinding.FragmentProductDetailBinding;
import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.utils.Constants;
import com.squareup.picasso.Picasso;


public class ProductDetailFragment extends Fragment {
    private FragmentProductDetailBinding binding;
    private ProductDetailViewModel productDetailViewModel;
    public static final String ARG_PRODUCT_ID = "PRODUCT_ID_ARG";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProductRepository productRepository = new ProductRepository(requireContext());
        productDetailViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                //noinspection unchecked
                return (T) new ProductDetailViewModel(productRepository);
            }
        }).get(ProductDetailViewModel.class);

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        setToolbar();

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

        binding.buyButton.setOnClickListener(v -> productDetailViewModel.addToCart());

        productDetailViewModel.productAddedToCart.observe(getViewLifecycleOwner(), added -> {
            if (added != null && added) {
                Toast.makeText(getContext(), "Produto adicionado ao carrinho com sucesso!", Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.navigation_cart);
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

    private void setToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Detalhes");
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        if (toolbar.getNavigationIcon() != null) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.white, requireActivity().getTheme()));
        }

        toolbar.setNavigationOnClickListener(view -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack(R.id.navigation_home, false);
        });
    }

    private void updateUI(@NonNull Product product) {

        insertImageView(product.getImageSrc());
        if(product.getAdditionalImagesSrc() != null) {
            for (String imageUrl : product.getAdditionalImagesSrc()) {
                insertImageView(imageUrl);
            }
        }

        binding.productNameTextView.setText(product.getTitle());
        binding.productPriceTextView.setText(String.format("R$ %.2f", product.getPrice()));
        binding.productInstallmentTextView.setText(
                String.format("ou até %dx de R$ %.2f", product.getInstallments(), product.getPrice() / product.getInstallments())
        );
        binding.productDescriptionTextView.setText(product.getDescription());
    }

    private void insertImageView(String resource) {
        if(resource == null) return;

        ImageView imageView = new ImageView(this.getContext());

        int sizeDpWidth = 410;
        int sizeDpHeight = 300;

        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        dpToPixel(sizeDpWidth),
                        dpToPixel(sizeDpHeight)
                )
        );

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8, 8);

        Picasso.get()
                .load(Constants.BASE_URL + resource)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView);

        binding.imagesContainer.addView(imageView);
    }

    public int dpToPixel(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}