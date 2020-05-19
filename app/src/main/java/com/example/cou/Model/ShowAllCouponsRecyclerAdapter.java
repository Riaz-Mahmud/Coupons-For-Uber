package com.example.cou.Model;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cou.Network.ApiClient;
import com.example.cou.Network.ApiInterface;
import com.example.cou.R;
import com.example.cou.ShowAllCouponsAdmin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowAllCouponsRecyclerAdapter extends RecyclerView.Adapter<ShowAllCouponsRecyclerAdapter.MyViewHolder>{

    private List<ShowAllCoupons> contacts;
    private Context mcontext;
    ViewGroup prnt;

    public ShowAllCouponsRecyclerAdapter(List<ShowAllCoupons> contacts, Context context)
    {
        this.contacts = contacts;
        this.mcontext =  context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.prnt= parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.couponsshowadmin,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.codeTitleAdminPanel.setText(contacts.get(position).getCodeTitle());
        holder.CouponCodeAdminPanel.setText(contacts.get(position).getCode());
        holder.CouponStatusAdminPanel.setText("Status: "+contacts.get(position).getStatus());

        if (contacts.get(position).getStatus().equals("active")){
            holder.ActiveCoupon.setVisibility(View.GONE);
            holder.DeactiveCoupon.setVisibility(View.VISIBLE);
        }else if (contacts.get(position).getStatus().equals("deactive")){
            holder.ActiveCoupon.setVisibility(View.VISIBLE);
            holder.DeactiveCoupon.setVisibility(View.GONE);
        }

        holder.DeactiveCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (false != checkConnection()) {

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<String> call = apiService.DeactiveStatus(contacts.get(position).getId());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                if (response.body().equals("1")) {
                                    Toast.makeText(mcontext, "Success!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(mcontext, ShowAllCouponsAdmin.class);
                                    mcontext.startActivity(intent);

                                } else if (response.body().equals("2")) {
                                    Toast.makeText(mcontext, "Something going wrong!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mcontext, "Something going wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            response.body();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });

        holder.ActiveCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (false != checkConnection()){

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<String> call = apiService.ActiveStatus(contacts.get(position).getId());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                if (response.body().equals("1")){
                                    Toast.makeText(mcontext, "Success!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mcontext,ShowAllCouponsAdmin.class);
                                    mcontext.startActivity(intent);
                                }else if (response.body().equals("2")){
                                    Toast.makeText(mcontext, "Something going wrong!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(mcontext, "Something going wrong!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            response.body();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });

        holder.deleteCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (false != checkConnection()){

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<String>call = apiService.DeleteCoupon(contacts.get(position).getId());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                if (response.body().equals("1")){
                                    Toast.makeText(mcontext, "Success!",Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(mcontext,ShowAllCouponsAdmin.class);
                                    mcontext.startActivity(intent);

                                }else if (response.body().equals("2")){
                                    Toast.makeText(mcontext, "Something going wrong!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(mcontext, "Something going wrong!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            response.body();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                }
            }
        });

    }

    public boolean checkConnection()
    {
        ConnectivityManager manager = (ConnectivityManager)
                mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork== null)
        {
            Toast.makeText(mcontext, "No Internet Connection",Toast.LENGTH_SHORT).show();
            return false;

        }else{
            return true;
        }
    }



    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView codeTitleAdminPanel, CouponCodeAdminPanel,CouponStatusAdminPanel;
        Button ActiveCoupon,DeactiveCoupon,deleteCoupon;

        public MyViewHolder( View itemView) {
            super(itemView);
            codeTitleAdminPanel = itemView.findViewById(R.id.codeTitleAdminPanel);
            CouponCodeAdminPanel = itemView.findViewById(R.id.CouponCodeAdminPanel);
            ActiveCoupon = itemView.findViewById(R.id.ActiveCoupon);
            DeactiveCoupon = itemView.findViewById(R.id.DeactiveCoupon);
            deleteCoupon = itemView.findViewById(R.id.deleteCoupon);
            CouponStatusAdminPanel = itemView.findViewById(R.id.CouponStatusAdminPanel);
        }
    }





}
