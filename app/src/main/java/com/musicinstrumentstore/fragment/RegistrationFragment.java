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
import com.musicinstrumentstore.network_service.dto.request.RegistrationRequestDto;
import com.musicinstrumentstore.network_service.dto.response.TokenResponseDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    private TextInputEditText username, email, password, address, flat, phone;
    private Button regBtn;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.etUsername);
        email = view.findViewById(R.id.etEmail);
        password = view.findViewById(R.id.etPassword);
        address = view.findViewById(R.id.etAddress);
        flat = view.findViewById(R.id.etFlat);
        phone = view.findViewById(R.id.etPhone);

        regBtn = view.findViewById(R.id.btnRegister);
        progressBar = view.findViewById(R.id.progressBar);
        TokenManager tokenManager = TokenManager.getInstance(getContext());

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newUserName = username.getText().toString().trim();
                String newEmail = email.getText().toString().trim();
                String newPassword = password.getText().toString().trim();
                String newAddress = address.getText().toString().trim();
                int newFlat = Integer.parseInt(flat.getText().toString().trim());
                String newPhone = phone.getText().toString().trim();

                regBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                RetrofitClient.getInstance(getContext()).getAuthApi()
                        .registerUser(new RegistrationRequestDto(newUserName,newEmail,newPassword,newAddress,newFlat,newPhone))
                        .enqueue(new Callback<TokenResponseDto>() {
                            @Override
                            public void onResponse(Call<TokenResponseDto> call, Response<TokenResponseDto> response) {
                                progressBar.setVisibility(View.GONE);
                                regBtn.setVisibility(View.VISIBLE);

                                if(response.isSuccessful() && response.body() != null) {
                                    tokenManager.saveTokens(response.body().getMyAccessToken(), response.body().getMyRefreshToken());
                                    Navigation.findNavController(view).navigate(R.id.reg_to_catalog);
                                }
                                else {
                                    Toast.makeText(getContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenResponseDto> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                regBtn.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "Ошибка сети", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }
}