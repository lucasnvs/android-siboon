package com.lucasnvs.siboon.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.model.Section;
import com.lucasnvs.siboon.ui.productdetail.ProductDetailFragment;
import com.lucasnvs.siboon.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public final List<Product> productList;
    private final Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText(String.format("R$ %s", product.getPrice()));

        Picasso.get().load(Constants.BASE_URL + product.getImageSrc())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);

            Bundle args = new Bundle();
            args.putLong(ProductDetailFragment.ARG_PRODUCT_ID, product.getId());

            navController.navigate(R.id.navigation_product_detail, args);
        });

        holder.buyButton.setOnClickListener(v -> {
            upsertOnCart(product);

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.navigation_cart);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView price;
        Button buyButton;
        ImageButton favoriteButton;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);
            buyButton = itemView.findViewById(R.id.buy_button);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }

    public void setProducts(List<Product> products) {
        this.productList.clear();
        this.productList.addAll(products);
    }

    public void upsertOnCart(Product product) {
        Log.d("ProductAdapter", "Product Id:" + product.getId());
        compositeDisposable.add(
                new ProductRepository(context).upsertCartProduct(product, 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d("ProductAdapter", "Produto adicionado ao carrinho com sucesso!"),
                                throwable -> Log.e("ProductAdapter", "Erro ao adicionar produto ao carrinho.", throwable))
        );
    }
}
