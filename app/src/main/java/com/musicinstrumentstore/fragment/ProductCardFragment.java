package com.musicinstrumentstore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.network_service.dto.response.ProductCardResponseDto;
import com.musicinstrumentstore.network_service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductCardFragment extends Fragment {

    private ShimmerFrameLayout shimmerLayout;
    private ImageView imageProduct;
    private TextView nameProduct;
    private TextView descriptionProduct;
    private final String BASE_URL = "http://192.168.100.212:8080";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        shimmerLayout = view.findViewById(R.id.shimmerLayout_card);
        imageProduct = view.findViewById(R.id.image_product_card);
        nameProduct = view.findViewById(R.id.product_name);
        descriptionProduct = view.findViewById(R.id.description_product);

        int id = getArguments().getInt("Product_id");

        RetrofitClient.getInstance(getContext()).getCatalogApi().getProduct(id).enqueue(new Callback<ProductCardResponseDto>() {
            @Override
            public void onResponse(Call<ProductCardResponseDto> call, Response<ProductCardResponseDto> response) {
                if (shimmerLayout != null) {
                    shimmerLayout.stopShimmer();
                    shimmerLayout.setVisibility(View.GONE);
                }
                if (response.isSuccessful() && response.body() != null){
                    imageProduct.setVisibility(View.VISIBLE);
                    nameProduct.setVisibility(View.VISIBLE);
                    descriptionProduct.setVisibility(View.VISIBLE);
                    String FullUrl = BASE_URL + response.body().getImageUrl();
                    Glide.with(getContext())
                            .load(FullUrl)
                            .error(R.drawable.image_error)
                            .into(imageProduct);
                    nameProduct.setText(response.body().getName());
                    descriptionProduct.setText(response.body().getDescription());
                }
            }

            @Override
            public void onFailure(Call<ProductCardResponseDto> call, Throwable t) {
                if (shimmerLayout != null) {
                    shimmerLayout.stopShimmer();
                    shimmerLayout.setVisibility(View.GONE);
                }

            }
        });
    }
}