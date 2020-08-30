package com.ebookfrenzy.myapplication.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);
    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @POST("checkUser")
    @FormUrlEncoded
    Observable<String> checkUser(@Field("email") String email);

    @POST("updatePSWD")
    @FormUrlEncoded
    Observable<String> updatePSWD(@Field("email") String email,
                                  @Field("password") String password);

    @POST("updateUser")
    @FormUrlEncoded
    Observable<String> updateUser(@Field("email") String email,
                                  @Field("name") String name,
                                  @Field("password") String password);

}
