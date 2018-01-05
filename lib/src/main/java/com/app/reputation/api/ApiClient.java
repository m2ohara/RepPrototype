package com.app.reputation.api;

import android.widget.Toast;

import com.app.reputation.api.ApiInterface;
import com.app.reputation.api.Expression;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by michael on 02/01/18.
 */

public class ApiClient {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    private String endpoint = "http://10.0.2.2:5000";
    private Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl(endpoint)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

    private ApiInterface apiCall = retrofit.create(ApiInterface.class);


    public void GetAll(String UserId, final ExpressionCallback expressionCallback) {

        Call<List<Expression>> call = apiCall.getAll();

        call.enqueue(new Callback<List<Expression>>() {
            @Override
            public void onResponse(Call<List<Expression>> call, Response<List<Expression>> response) {

                if(response.isSuccessful()) {
                    expressionCallback.OnSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Expression>> call, Throwable t) {


            }
        });
    }

    public void Create(Expression expression, final ExpressionCallback expressionCallback) {

        Call<Expression> call = apiCall.create(expression);

        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {
                if(response.isSuccessful()) {
                    expressionCallback.OnSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {

            }
        });
    }
}
