package com.musicinstrumentstore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.adapter.CatalogAdapter;
import com.musicinstrumentstore.network_service.RetrofitClient;
import com.musicinstrumentstore.network_service.dto.response.CatalogProductsResponseDto;

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

    private int currentPage = 0;
    private String currentCategoryName = null;
    private final int PAGE_SIZE = 10;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private GridLayoutManager gridLayoutManager;

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

        CatalogAdapter.OnProductCardClickListener onProductCardClickListener = new CatalogAdapter.OnProductCardClickListener() {
            @Override
            public void onProductCardClick(CatalogProductsResponseDto productCard, int position) {
                Bundle id = new Bundle();
                id.putInt("Product_id", productCard.getId());
                Navigation.findNavController(view).navigate(R.id.product_card, id);
            }

            @Override
            public void onAddToBasketProductCardClick(int productId) {
               RetrofitClient.getInstance(getContext()).getCatalogApi().addToCart(productId)
                       .enqueue(new Callback<Void>() {
                           @Override
                           public void onResponse(Call<Void> call, Response<Void> response) {
                               if(response.isSuccessful()) {
                                   Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Toast.makeText(getContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onFailure(Call<Void> call, Throwable t) {
                               Toast.makeText(getContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
                           }
                       });
            }

        };

        catalogAdapter = new CatalogAdapter(getContext(), products,onProductCardClickListener);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);

        catalogRecycler = view.findViewById(R.id.catalog_recycler);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        catalogRecycler.setLayoutManager(gridLayoutManager);
        catalogRecycler.setAdapter(catalogAdapter);

        catalogRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && !isLastPage) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            currentPage++;
                            loadNextPage();
                        }
                    }
                }
            }
        });
        if (getArguments() != null && getArguments().containsKey("CategoryName")) {
            currentCategoryName = getArguments().getString("CategoryName");
        } else {
            currentCategoryName = null;
        }
        loadFirstPage();
    }


    private void loadFirstPage() {
        isLoading = true;

        if(currentCategoryName == null) {
            RetrofitClient.getInstance(getContext()).getCatalogApi()
                    .getAllProduct(0, PAGE_SIZE).enqueue(new Callback<List<CatalogProductsResponseDto>>() {
                        @Override
                        public void onResponse(Call<List<CatalogProductsResponseDto>> call, Response<List<CatalogProductsResponseDto>> response) {
                            isLoading = false;

                            if (shimmerLayout != null) {
                                shimmerLayout.stopShimmer();
                                shimmerLayout.setVisibility(View.GONE);
                            }

                            if (response.isSuccessful() && response.body() != null) {
                                catalogRecycler.setVisibility(View.VISIBLE);
                                catalogAdapter.updateDate(response.body());

                                if (response.body().size() < PAGE_SIZE) {
                                    isLastPage = true;
                                }
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
        else {
            RetrofitClient.getInstance(getContext()).getCatalogApi()
                    .getAllFromCategory(currentCategoryName,0, PAGE_SIZE).enqueue(new Callback<List<CatalogProductsResponseDto>>() {
                        @Override
                        public void onResponse(Call<List<CatalogProductsResponseDto>> call, Response<List<CatalogProductsResponseDto>> response) {
                            isLoading = false;

                            if (shimmerLayout != null) {
                                shimmerLayout.stopShimmer();
                                shimmerLayout.setVisibility(View.GONE);
                            }

                            if (response.isSuccessful() && response.body() != null) {
                                catalogRecycler.setVisibility(View.VISIBLE);
                                catalogAdapter.updateDate(response.body());

                                if (response.body().size() < PAGE_SIZE) {
                                    isLastPage = true;
                                }
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
    }

    private void loadNextPage() {
        isLoading = true;

        if(currentCategoryName == null) {
            RetrofitClient.getInstance(getContext()).getCatalogApi()
                    .getAllProduct(currentPage, PAGE_SIZE).enqueue(new Callback<List<CatalogProductsResponseDto>>() {
                        @Override
                        public void onResponse(Call<List<CatalogProductsResponseDto>> call, Response<List<CatalogProductsResponseDto>> response) {
                            isLoading = false;
                            if (response.isSuccessful() && response.body() != null) {
                                List<CatalogProductsResponseDto> newItems = response.body();
                                if (!newItems.isEmpty()) {
                                    catalogAdapter.addItems(newItems);
                                }
                                if (newItems.size() < PAGE_SIZE) {
                                    isLastPage = true;
                                }
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
        else {
            RetrofitClient.getInstance(getContext()).getCatalogApi()
                    .getAllFromCategory(currentCategoryName,currentPage, PAGE_SIZE).enqueue(new Callback<List<CatalogProductsResponseDto>>() {
                        @Override
                        public void onResponse(Call<List<CatalogProductsResponseDto>> call, Response<List<CatalogProductsResponseDto>> response) {
                            isLoading = false;
                            if (response.isSuccessful() && response.body() != null) {
                                List<CatalogProductsResponseDto> newItems = response.body();
                                if (!newItems.isEmpty()) {
                                    catalogAdapter.addItems(newItems);
                                }
                                if (newItems.size() < PAGE_SIZE) {
                                    isLastPage = true;
                                }
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