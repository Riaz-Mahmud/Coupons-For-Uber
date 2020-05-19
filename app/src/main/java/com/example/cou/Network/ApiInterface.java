package com.example.cou.Network;

import com.example.cou.Model.CPContact;
import com.example.cou.Model.ShowAllCoupons;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("taxicoupon/login.php")
    Call<String> getLoginResponse(
            @Field("username") String Username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("taxicoupon/updatecoupon.php")
    Call<String> submitNewCoupons(
            @Field("codetitle") String CodeTitle,
            @Field("code") String code,
            @Field("uses") String Uses,
            @Field("saveds") String Saves,
            @Field("type") String Type
    );

    @FormUrlEncoded
    @POST("taxicoupon/allcouponsadminpanel.php")
    Call<List<ShowAllCoupons>> allCouponsReadAdminPanel(
            @Field("type") String Type
    );

    @FormUrlEncoded
    @POST("taxicoupon/updatestatusdeactive.php")
    Call<String> DeactiveStatus(
            @Field("id") String ID
    );

    @FormUrlEncoded
    @POST("taxicoupon/updatestatusactive.php")
    Call<String> ActiveStatus(
            @Field("id") String ID
    );

    @FormUrlEncoded
    @POST("taxicoupon/updatestatusdelete.php")
    Call<String> DeleteCoupon(
            @Field("id") String ID
    );


    @FormUrlEncoded
    @POST("taxicoupon/readallcupon.php")
    Call<List<CPContact>> getUserGroupResponse(
            @Field("username") String Username
    );


}
