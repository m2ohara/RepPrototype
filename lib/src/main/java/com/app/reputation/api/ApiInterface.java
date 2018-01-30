package com.app.reputation.api;

import com.app.reputation.api.UserExpression.Expression;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by michael on 02/01/18.
 */

public interface ApiInterface {

    @Headers("Cache-Control: public, max-age=640000, s-maxage=640000 , max-stale=2419200")
    @GET("api/UserExpression")
    Call<List<Expression>> getAll();

    @POST("api/UserExpression")
    Call<Expression> create(@Body Expression expression);

    @PUT("api/UserExpression")
    Call<Expression> update(@Body Expression expression);
}
