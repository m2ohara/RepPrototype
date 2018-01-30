package com.app.reputation.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.reputation.api.UserExpression.Expression;
import com.app.reputation.api.UserExpression.ExpressionCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by michael on 02/01/18.
 */

public class ApiClient {

    public ApiClient(Context context) {
        int cacheSize = 5 * 1024 * 1024;
        cache = new Cache(context.getCacheDir(), cacheSize);
        this.context = context;
    }

    private Cache cache;
    private static Context context;

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    private String endpoint = "http://10.0.2.2:5000";
    private Retrofit retrofit =
            new Retrofit
                    .Builder()
                    .baseUrl(endpoint)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

    private ApiInterface apiCall = retrofit.create(ApiInterface.class);

    public ApiInterface GetApiCall() {
        return  apiCall;
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }


    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365,TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if(isNetworkAvailable(context)){
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            okhttp3.Response originalResponse = chain.proceed(request);
            if (isNetworkAvailable(context)) {
                int maxAge = 60  * 60; // read from cache
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

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

                expressionCallback.onError();
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

                expressionCallback.onError();
            }
        });
    }

    public void Execute(final ExpressionCallback expressionCallback, Call<Expression> call) {

        call.enqueue(new Callback<Expression>() {
            @Override
            public void onResponse(Call<Expression> call, Response<Expression> response) {

            }

            @Override
            public void onFailure(Call<Expression> call, Throwable t) {

            }
        });
    }

}
