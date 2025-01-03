package com.lucasnvs.siboon.ui.cart;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.data.repository.ProductRepository;
import com.lucasnvs.siboon.model.Cart;
import com.lucasnvs.siboon.model.Product;
import com.lucasnvs.siboon.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final List<Cart> carts;
    private final Context context;

    private ProductRepository productRepository;

    public CartAdapter(Context context, List<Cart> cart) {
        this.context = context;
        this.carts = cart;
    }

    @Override
    public void onViewRecycled(@NonNull CartViewHolder holder) {
        super.onViewRecycled(holder);
        compositeDisposable.clear();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);


        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = carts.get(position);
        Product product = cart.getProduct();

        holder.title.setText(product.getTitle());
        holder.price.setText(String.format("R$ %.2f", product.getPrice()));
        holder.installment.setText(String.format("Parcelado no cartão em até %dx", product.getInstallments()));
        holder.quantity.setText(String.valueOf(cart.getQuantity()));


        productRepository = new ProductRepository(context);

        Picasso.get().load(Constants.BASE_URL + product.getImageSrc())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.image);

        holder.btnRemove.setOnClickListener(v -> removeOnCart(product));

        holder.btnDecrease.setOnClickListener(v -> {
            compositeDisposable.add(
                    productRepository.decreaseCartProduct(product.getId(), 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    quantity -> {
                                        holder.quantity.setText(String.valueOf(quantity));
                                    },
                                    throwable -> {
                                        Toast.makeText(context, "Erro ao remover produto do carrinho.", Toast.LENGTH_LONG).show();
                                        Log.e("CartAdapter", "Erro ao remover produto do carrinho.", throwable);
                                    })
            );
        });

        holder.btnIncrease.setOnClickListener(v -> {
            compositeDisposable.add(
                    productRepository.upsertCartProduct(product.getId(), 1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    response -> {
                                        holder.quantity.setText(String.valueOf(response.getQuantity()));
                                    },
                                    throwable -> {
                                        Log.e("CartAdapter", "Erro ao adicionar produto ao carrinho.", throwable);
                                    }
                            )
            );
        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        final ImageView image;
        final TextView title;
        final TextView price;
        final TextView installment;
        final TextView quantity;
        final ImageView btnDecrease;
        final ImageView btnIncrease;
        final ImageView btnRemove;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgProduct);
            title = itemView.findViewById(R.id.tvProductName);
            price = itemView.findViewById(R.id.tvPriceHighlight);
            installment = itemView.findViewById(R.id.tvPriceInstallment);
            quantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    public void setProducts(List<Cart> products) {
        this.carts.clear();
        this.carts.addAll(products);
    }

    public void removeOnCart(Product product) {
        Log.d("CartAdapter", "Product Id:" + product.getId());
        compositeDisposable.add(
                productRepository.deleteById(product.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Toast.makeText(context, "Produto removido do carrinho com sucesso!", Toast.LENGTH_LONG).show(),
                                throwable -> {
                                    Toast.makeText(context, "Erro ao remover produto do carrinho.", Toast.LENGTH_LONG).show();
                                    Log.e("CartAdapter", "Erro ao remover produto do carrinho.", throwable);
                                })
        );
    }
}
