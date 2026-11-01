package com.musicinstrumentstore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.adapter.CatalogAdapter;
import com.musicinstrumentstore.adapter.ProductCard;
import com.musicinstrumentstore.network_service.RetrofitClient;
import com.musicinstrumentstore.network_service.dto.CatalogProductsResponseDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CatalogFragment extends Fragment {

    private RecyclerView catalogRecycler;
    private CatalogAdapter catalogAdapter;
    private List<CatalogProductsResponseDto> products;
    private ShimmerFrameLayout shimmerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        products = new ArrayList<>();

        catalogAdapter = new CatalogAdapter(getContext(), products);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        catalogRecycler = view.findViewById(R.id.catalog_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        catalogRecycler.setLayoutManager(gridLayoutManager);

        catalogRecycler.setAdapter(catalogAdapter);

        RetrofitClient.getInstance(getContext())
                .getCatalogApi().getAllProduct().enqueue(new Callback<List<CatalogProductsResponseDto>>() {
                    @Override
                    public void onResponse(Call<List<CatalogProductsResponseDto>> call, Response<List<CatalogProductsResponseDto>> response) {

                        if (shimmerLayout != null) {
                            shimmerLayout.stopShimmer();
                            shimmerLayout.setVisibility(View.GONE);
                        }

                        if(response.isSuccessful() && response.body() != null){
                            catalogRecycler.setVisibility(View.VISIBLE);
                            catalogAdapter.updateDate(response.body());
                        }
                        else {
                            Toast.makeText(getContext(), "Ошибка доступа. Код: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<CatalogProductsResponseDto>> call, Throwable t) {
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