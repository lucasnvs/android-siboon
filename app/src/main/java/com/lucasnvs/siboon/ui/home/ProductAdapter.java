package com.lucasnvs.siboon.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasnvs.siboon.R;
import com.lucasnvs.siboon.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

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
        holder.price.setText("R$ " + product.getPrice());

        Picasso.get().load("http://10.0.2.2/siboon/" + product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.image);

        // Implementar clique para o botão de compra e favoritar, se necessário
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
}
