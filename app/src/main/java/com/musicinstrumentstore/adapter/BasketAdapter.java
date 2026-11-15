package com.musicinstrumentstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.network_service.dto.response.ProductCardResponseDto;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    public interface OnBasketCardClickListener{
        void onBasketCardClick(ProductCardResponseDto basketCard, int position);
    }

    private final BasketAdapter.OnBasketCardClickListener onBasketCardClickListener;
    private final LayoutInflater inflater;
    private final List<ProductCardResponseDto> basketCards;
    private final String BASE_URL = "http://192.168.100.212:8080";

    public BasketAdapter(Context context, List<ProductCardResponseDto> basketCards, BasketAdapter.OnBasketCardClickListener onBasketCardClickListener) {
        this.onBasketCardClickListener = onBasketCardClickListener;
        this.inflater = LayoutInflater.from(context);
        this.basketCards = basketCards;
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BasketAdapter.ViewHolder(inflater.inflate(R.layout.basket_product_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
        ProductCardResponseDto basketCard = basketCards.get(position);
        String FullUrl = BASE_URL + basketCard.getImageUrl();
        Glide.with(holder.itemView.getContext())
                .load(FullUrl)
                .error(R.drawable.image_error)
                .into(holder.productImg);
        holder.productName.setText(basketCard.getName());
        holder.productPrice.setText(basketCard.getPrice());
        holder.productDescription.setText(basketCard.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    onBasketCardClickListener.onBasketCardClick(basketCards.get(pos), pos);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return basketCards.size();
    }

    public void updateData(List<ProductCardResponseDto> newItems) {
        this.basketCards.clear();
        this.basketCards.addAll(newItems);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.basketCards.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.basketCards.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImg;
        private TextView productName, productDescription, productPrice;
        private Button deleteBtn,arrangeBtn;
        ViewHolder(View view){
            super(view);
            productImg = view.findViewById(R.id.productImage);
            productName = view.findViewById(R.id.tvProductPrice);
            productDescription = view.findViewById(R.id.tvProductDescription);
            productPrice = view.findViewById(R.id.tvProductPrice);
            deleteBtn = view.findViewById(R.id.delete_from_basket);
            arrangeBtn = view.findViewById(R.id.buy_from_basket);
        }
    }
}
