package com.example.cou;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cou.Network.ApiClient;
import com.example.cou.Network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBashBoard_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView errorSubmitTxt;
    Button submitCoupon, showallcoupons;
    EditText titleAdminPanel, codeAdminPanel, usesEdittxt, savedEditTxt;
    Spinner categoryList;
    private ProgressDialog LoadingBar;

    String TypeOfCategory;
    String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bash_board_);

        titleAdminPanel = findViewById(R.id.titleAdminPanel);
        codeAdminPanel = findViewById(R.id.codeAdminPanel);
        usesEdittxt = findViewById(R.id.usesEdittxt);
        savedEditTxt = findViewById(R.id.savedEditTxt);
        errorSubmitTxt = findViewById(R.id.errorSubmitTxt);
        submitCoupon = findViewById(R.id.submitCoupon);
        showallcoupons = findViewById(R.id.showallcoupons);
        categoryList = findViewById(R.id.categoryList);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(adapter);
        categoryList.setOnItemSelectedListener(this);



        submitCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitCoupon();
            }
        });

        showallcoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBashBoard_Activity.this, ShowAllCouponsAdmin.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TypeOfCategory =parent.getItemAtPosition(position).toString();

        if (TypeOfCategory.equals("Discount Codes")){
            Type="discountcodes";
        }else if (TypeOfCategory.equals("Promo Codes")){
            Type="promocodes";
        }else if (TypeOfCategory.equals("Cash Back")){
            Type="cashback";
        }else if (TypeOfCategory.equals("Hot Deals")){
            Type="hotdeals";
        }else if (TypeOfCategory.equals("Free Rides")){
            Type="freerides";
        }else if (TypeOfCategory.equals("Free Gifts")){
            Type="freegifts";
        }else {
            Type=null;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void SubmitCoupon() {

        String Title = titleAdminPanel.getText().toString();
        String Code = codeAdminPanel.getText().toString();
        String Uses = usesEdittxt.getText().toString();
        String Saves = savedEditTxt.getText().toString();
        if(Title.trim().isEmpty() || Code.trim().isEmpty() || Type.trim().isEmpty() || Type.trim().equals(null) || Uses.trim().isEmpty() || Saves.trim().isEmpty())
        {
            if(Title.trim().isEmpty())
            {
                titleAdminPanel.requestFocus();
                return;
            }
            if(Code.trim().isEmpty())
            {
                codeAdminPanel.requestFocus();
                return;
            }
            if(Uses.trim().isEmpty())
            {
                usesEdittxt.requestFocus();
                return;
            }
            if(Saves.trim().isEmpty())
            {
                savedEditTxt.requestFocus();
                return;
            }
            if(Type.trim().isEmpty()|| Type.trim().equals(null))
            {
                Toast.makeText(AdminBashBoard_Activity.this, "Select The coupon Category",Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            if(false!=checkConnection()){
                LoadingBar = new ProgressDialog(AdminBashBoard_Activity.this);
                LoadingBar.setMessage("Loading...");LoadingBar.setCanceledOnTouchOutside(false);LoadingBar.show();

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<String>call = apiService.submitNewCoupons(Title,Code,Uses,Saves,Type);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            if (response.body().equals("1")){
                                errorSubmitTxt.setTextColor(Color.GREEN);
                                errorSubmitTxt.setText("Coupons Update Success!");
                                titleAdminPanel.setText(null);
                                codeAdminPanel.setText(null);
                                usesEdittxt.setText(null);
                                savedEditTxt.setText(null);

                            }else if (response.body().equals("0")){
                                errorSubmitTxt.setText("Field Update!");
                            }else {
                                Toast.makeText(AdminBashBoard_Activity.this, "Something Wrong!",Toast.LENGTH_SHORT).show();
                                errorSubmitTxt.setText("Something Wrong!");
                            }
                        }
                        response.body();
                        LoadingBar.cancel();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

            }else {
                Toast.makeText(AdminBashBoard_Activity.this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork== null) {
            Toast.makeText(AdminBashBoard_Activity.this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


}
