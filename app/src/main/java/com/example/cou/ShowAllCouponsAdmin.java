package com.example.cou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cou.Model.ShowAllCoupons;
import com.example.cou.Model.ShowAllCouponsRecyclerAdapter;
import com.example.cou.Network.ApiClient;
import com.example.cou.Network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllCouponsAdmin extends AppCompatActivity {

    private ApiInterface apiInterface;
    private RecyclerView.LayoutManager layoutManager;
    private ShowAllCouponsRecyclerAdapter cpadapter;
    private List<ShowAllCoupons> cpcontacts;
    private RecyclerView recyclerView;
    public ProgressDialog LoadingBar;

    TextView codeTitleAdminPanel, CouponCodeAdminPanel,CouponStatusAdminPanel;
    Button ActiveCoupon,DeactiveCoupon,deleteCoupon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_coupons_admin);

        recyclerView = findViewById(R.id.recyclerView);
        codeTitleAdminPanel = findViewById(R.id.codeTitleAdminPanel);
        CouponStatusAdminPanel = findViewById(R.id.CouponStatusAdminPanel);
        CouponCodeAdminPanel = findViewById(R.id.CouponCodeAdminPanel);
        ActiveCoupon = findViewById(R.id.ActiveCoupon);
        DeactiveCoupon = findViewById(R.id.DeactiveCoupon);
        deleteCoupon = findViewById(R.id.deleteCoupon);

        layoutManager = new LinearLayoutManager(ShowAllCouponsAdmin.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        ShowAllCouponsAdminPanel();


    }

    public void ShowAllCouponsAdminPanel() {

        if (false!=checkConnection()){

            String type = null;
            LoadingBar = new ProgressDialog(ShowAllCouponsAdmin.this);
            LoadingBar.setMessage("Loading...");LoadingBar.setCanceledOnTouchOutside(false);LoadingBar.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<ShowAllCoupons>> call = apiService.allCouponsReadAdminPanel(type);

            call.enqueue(new Callback<List<ShowAllCoupons>>() {
                @Override
                public void onResponse(Call<List<ShowAllCoupons>> call, Response<List<ShowAllCoupons>> response) {
                    cpcontacts = response.body();
                    cpadapter = new ShowAllCouponsRecyclerAdapter(cpcontacts,ShowAllCouponsAdmin.this);
                    recyclerView.setAdapter(cpadapter);
                    LoadingBar.cancel();
                }

                @Override
                public void onFailure(Call<List<ShowAllCoupons>> call, Throwable t) {

                }
            });
        }


    }

    public boolean checkConnection()
    {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork== null)
        {
            Toast.makeText(ShowAllCouponsAdmin.this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            return false;

        }else{
            return true;
        }
    }
}
