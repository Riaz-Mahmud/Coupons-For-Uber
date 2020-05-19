package com.example.cou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cou.Model.CPContact;
import com.example.cou.Model.CPRecyclerAdapter;
import com.example.cou.Network.ApiClient;
import com.example.cou.Network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCode extends AppCompatActivity {

    private ApiInterface apiInterface;
    private RecyclerView.LayoutManager layoutManager;
    private CPRecyclerAdapter cpadapter;
    private List<CPContact> cpcontacts;
    private RecyclerView recyclerView;
    public ProgressDialog LoadingBar;

    TextView codeTitle, code,usesTxtShowCode, savedTxtShowCode;
    Button codeShowBtn;
    ImageView barcodeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

        recyclerView = findViewById(R.id.recyclerView);
        codeTitle = findViewById(R.id.codeTitle);
        code = findViewById(R.id.code);
        codeShowBtn = findViewById(R.id.codeShowBtn);
        barcodeImg = findViewById(R.id.barcodeImg);
        usesTxtShowCode = findViewById(R.id.usesTxtShowCode);
        savedTxtShowCode = findViewById(R.id.savedTxtShowCode);

        layoutManager = new LinearLayoutManager(ShowCode.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        if (getIntent().hasExtra("Ctype")){
            String type = getIntent().getStringExtra("Ctype");
            ShowUserAllGroup(type);
        }else{
            Toast.makeText(ShowCode.this,"Something Wrong!",Toast.LENGTH_SHORT).show();
        }


    }

    private void ShowUserAllGroup(String type) {
        LoadingBar = new ProgressDialog(ShowCode.this);
        LoadingBar.setMessage("Loading...");LoadingBar.setCanceledOnTouchOutside(false);LoadingBar.show();
        String Ctype = type;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CPContact>> call = apiInterface.getUserGroupResponse(Ctype);

        call.enqueue(new Callback<List<CPContact>>() {
            @Override
            public void onResponse(Call<List<CPContact>> call, Response<List<CPContact>> response) {
                cpcontacts = response.body();
                cpadapter = new CPRecyclerAdapter(cpcontacts,ShowCode.this);
                recyclerView.setAdapter(cpadapter);
                LoadingBar.cancel();
            }

            @Override
            public void onFailure(Call<List<CPContact>> call, Throwable t) {

            }
        });
    }


}
