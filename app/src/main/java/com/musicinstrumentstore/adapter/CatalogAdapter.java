package com.musicinstrumentstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.network_service.dto.response.CatalogProductsResponseDto;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
   public interface OnProductCardClickListener{
        void onProductCardClick(CatalogProductsResponseDto productCard, int position);
       void onAddToBasketProductCardClick(int productId);

    }
    private final OnProductCardClickListener onProductCardClickListener;
    private final LayoutInflater inflater;
    private final List<CatalogProductsResponseDto> products;
    private final String BASE_URL = "http://192.168.100.212:8080";

    public CatalogAdapter(Context context, List<CatalogProductsResponseDto> products, OnProductCardClickListener onProductCardClickListener ) {
        this.onProductCardClickListener = onProductCardClickListener;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CatalogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.ViewHolder holder, int position) {
        CatalogProductsResponseDto productCard = products.get(position);
        String FullUrl = BASE_URL + productCard.getImageUrl();
        Glide.with(holder.itemView.getContext())
                .load(FullUrl)
                .error(R.drawable.image_error)
                .into(holder.imageView);
        holder.productName.setText(productCard.getProductName());
        holder.productCategory.setText(productCard.getProductCategory());
        holder.productPrice.setText(productCard.getProductPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    onProductCardClickListener.onProductCardClick(products.get(pos), pos);
                }
            }
        });

        holder.addToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    onProductCardClickListener.onAddToBasketProductCardClick(products.get(pos).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateDate(List<CatalogProductsResponseDto> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public void addItems(List<CatalogProductsResponseDto> newProducts) {
        int startPosition = this.products.size();
        this.products.addAll(newProducts);
        notifyItemRangeInserted(startPosition, newProducts.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView productName, productCategory, productPrice;
        private MaterialButton addToBasket;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.product_id);
            productName = view.findViewById(R.id.tvProductName);
            productCategory = view.findViewById(R.id.tvProductCategory);
            productPrice = view.findViewById(R.id.tvProductPrice);
            addToBasket = view.findViewById(R.id.add_to_basket);
        }
    }
}
