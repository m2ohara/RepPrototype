package com.app.reputation.api;

import com.app.reputation.api.Expression;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by michael on 02/01/18.
 */

public interface ApiInterface {

    @GET("api/UserExpression")
    Call<List<Expression>> getAll();

    @POST("api/UserExpression")
    Call<Expression> create(@Body Expression expression);
}
