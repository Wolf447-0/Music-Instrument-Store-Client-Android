package com.musicinstrumentstore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.musicinstrumentstore.R;

public class ProfileFragment extends Fragment {

    private ShimmerFrameLayout shimmerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

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