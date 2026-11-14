package com.musicinstrumentstore.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.musicinstrumentstore.R;
import com.musicinstrumentstore.network_service.RetrofitClient;
import com.musicinstrumentstore.network_service.TokenManager;
import com.musicinstrumentstore.network_service.dto.request.LogInRequestDto;
import com.musicinstrumentstore.network_service.dto.response.TokenResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInFragment extends Fragment {

    private TextInputEditText username, password;
    private Button logBtn;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.etLogin);
        password = view.findViewById(R.id.etPassword);


        logBtn = view.findViewById(R.id.btnLog);
        progressBar = view.findViewById(R.id.progressBar);
        TokenManager tokenManager = TokenManager.getInstance(getContext());

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserName = username.getText().toString().trim();
                String newPassword = password.getText().toString().trim();
                logBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                RetrofitClient.getInstance(getContext()).getAuthApi()
                        .loginUser(new LogInRequestDto(newUserName,newPassword))
                        .enqueue(new Callback<TokenResponseDto>() {
                            @Override
                            public void onResponse(Call<TokenResponseDto> call, Response<TokenResponseDto> response) {
                                progressBar.setVisibility(View.GONE);
                                logBtn.setVisibility(View.VISIBLE);

                                if(response.isSuccessful() && response.body() != null) {
                                    tokenManager.saveTokens(response.body().getMyAccessToken(), response.body().getMyRefreshToken());
                                    Navigation.findNavController(view).navigate(R.id.log_in_to_catalog);
                                }
                                else {
                                    Toast.makeText(getContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenResponseDto> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                logBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}