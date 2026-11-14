package com.musicinstrumentstore.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.musicinstrumentstore.R;
import com.musicinstrumentstore.network_service.dto.response.CatalogProductsResponseDto;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    public interface OnCategoryCardClickListener{
        void onCategoryCardClick(CategoryCard categoryCard, int position);
    }

    private final OnCategoryCardClickListener onCategoryCardClickListener;
    private final LayoutInflater inflater;
    private final List<CategoryCard> categories;

    public CategoryAdapter(Context context, List<CategoryCard> categories,OnCategoryCardClickListener onCategoryCardClickListener) {
        this.onCategoryCardClickListener = onCategoryCardClickListener;
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.category_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryCard category = categories.get(position);
        holder.imageView.setImageResource(category.getImage_flag());
        holder.textView.setText(category.getCategory_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    onCategoryCardClickListener.onCategoryCardClick(categories.get(pos), pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        ViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.image_category_id);
            textView = view.findViewById(R.id.name_category_id);
        }
    }
}
