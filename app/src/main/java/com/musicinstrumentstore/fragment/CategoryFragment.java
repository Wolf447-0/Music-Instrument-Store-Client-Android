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

import com.musicinstrumentstore.R;
import com.musicinstrumentstore.adapter.CategoryAdapter;
import com.musicinstrumentstore.adapter.CategoryCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        CategoryAdapter.OnCategoryCardClickListener onCategoryCardClickListener = new CategoryAdapter.OnCategoryCardClickListener() {
            @Override
            public void onCategoryCardClick(CategoryCard categoryCard, int position) {
                Bundle categoryName = new Bundle();
                categoryName.putString("CategoryName", categoryCard.getCategory_name());
                Navigation.findNavController(view).navigate(R.id.category_to_catalog,categoryName);
            }
        };
        categoryAdapter = new CategoryAdapter(getContext(),getAllCategory(),onCategoryCardClickListener);
        recyclerView = view.findViewById(R.id.category_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(categoryAdapter);

    }

    public List<CategoryCard> getAllCategory(){
        return new ArrayList<CategoryCard>(Arrays.asList(
                new CategoryCard(R.drawable.stringed,"Струнные"),
                new CategoryCard(R.drawable.keyboards, "Клавишные"),
                new CategoryCard(R.drawable.percussion, "Ударные"),
                new CategoryCard(R.drawable.wind_instruments, "Духовые"),
                new CategoryCard(R.drawable.electronic, "Электронные"),
                new CategoryCard(R.drawable.folk, "Народные"),
                new CategoryCard(R.drawable.orchestral, "Оркестровые"),
                new CategoryCard(R.drawable.vocal, "Вокальное оборудование"),
                new CategoryCard(R.drawable.studio, "Студийное оборудование"),
                new CategoryCard(R.drawable.lighting, "Световое оборудование")
        ));
    }

}