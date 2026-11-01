package com.musicinstrumentstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.network_service.RetrofitClient;
import com.musicinstrumentstore.network_service.dto.CatalogProductsResponseDto;

import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<CatalogProductsResponseDto> products;
    private final String BASE_URL = "http://192.168.100.212:8080";

    public CatalogAdapter(Context context, List<CatalogProductsResponseDto> products) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView productName, productCategory, productPrice;

        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.product_id);
            productName = view.findViewById(R.id.tvProductName);
            productCategory = view.findViewById(R.id.tvProductCategory);
            productPrice = view.findViewById(R.id.tvProductPrice);
        }
    }
}
