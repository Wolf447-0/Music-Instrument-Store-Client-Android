package com.musicinstrumentstore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.adapter.BasketAdapter;
import com.musicinstrumentstore.network_service.RetrofitClient;
import com.musicinstrumentstore.network_service.dto.response.ProductCardResponseDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketFragment extends Fragment {

    private ShimmerFrameLayout shimmerLayout;
    private RecyclerView basketRecycler;
    private BasketAdapter basketAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        shimmerLayout = view.findViewById(R.id.shimmerLayout_basket);
        basketRecycler = view.findViewById(R.id.basket_recycler);
        basketRecycler.setAdapter(basketAdapter);

        basketRecycler.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));
        List<ProductCardResponseDto> initialList = new ArrayList<>();

        BasketAdapter.OnBasketCardClickListener listener = new BasketAdapter.OnBasketCardClickListener() {
            @Override
            public void onBasketCardClick(ProductCardResponseDto basketCard, int position) {
                Bundle id = new Bundle();
                id.putInt("Product_id", basketCard.getId());
                androidx.navigation.Navigation.findNavController(view).navigate(R.id.basket_to_product_card, id);
            }
        };

        basketAdapter = new BasketAdapter(getContext(), initialList, listener);
        basketRecycler.setAdapter(basketAdapter);

        RetrofitClient.getInstance(getContext()).getCatalogApi().getAllBasketProduct()
                .enqueue(new Callback<List<ProductCardResponseDto>>() {
                    @Override
                    public void onResponse(Call<List<ProductCardResponseDto>> call, Response<List<ProductCardResponseDto>> response) {
                        if (shimmerLayout != null) {
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                        }
                        if (response.isSuccessful() && response.body() != null) {
                            basketRecycler.setVisibility(View.VISIBLE);
                            basketAdapter.updateData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductCardResponseDto>> call, Throwable t) {
                        if (shimmerLayout != null) {
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                        }
                        Toast.makeText(getContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (shimmerLayout != null && shimmerLayout.getVisibility() == View.VISIBLE) {
            shimmerLayout.startShimmer();
        }
    }

    @Override
    public void onPause() {
        if (shimmerLayout != null) {
            shimmerLayout.stopShimmer();
        }
        super.onPause();
    }


}