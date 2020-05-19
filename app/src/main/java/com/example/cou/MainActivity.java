package com.example.cou;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cou.Network.ApiClient;
import com.example.cou.Network.ApiInterface;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button cuponButton;
    TextView privacyPolicy, adminLoginTxt;
    private ProgressDialog LoadingBar;

    private InterstitialAd interstitialAd;

    //dialog
    TextView usernametxt, passwordtxt, errorshow;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Admob
        MobileAds.initialize(this,"ca-app-pub-2699591493465867~5559600553");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-2699591493465867/5191741371");
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

        privacyPolicy = findViewById(R.id.privacyPolicy);
        adminLoginTxt = findViewById(R.id.adminLoginTxt);
        cuponButton = findViewById(R.id.cuponButton);

        privacyPolicy.setPaintFlags(privacyPolicy.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        interstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed() {
                Intent intent = new Intent(MainActivity.this, CuponDashboard_Activity.class);
                startActivity(intent);
                interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
            }
        });

        cuponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else {
                    Intent intent = new Intent(MainActivity.this, CuponDashboard_Activity.class);
                    startActivity(intent);
                }

            }
        });

        adminLoginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this).setCancelable(true);
                final View mView = getLayoutInflater().inflate(R.layout.login_dialog,null);

                usernametxt = mView.findViewById(R.id.usernametxt);
                passwordtxt = mView.findViewById(R.id.passwordtxt);
                errorshow = mView.findViewById(R.id.errorshow);
                loginBtn = mView.findViewById(R.id.loginBtn);



                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                loginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginAdmin(dialog);
                    }
                });
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://discountscouponswebsite.blogspot.com/2020/01/privacy-policy.html")));
//                }catch (ActivityNotFoundException e){
//                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=" + "com.android.chrome"))); //mcontext.getPackageName()
//                }

                Intent intent = new Intent(MainActivity.this, privacyPolicy.class);
                startActivity(intent);

            }
        });
    }

    private void LoginAdmin(final AlertDialog dialog) {
        String username = usernametxt.getText().toString().toLowerCase();
        String password = passwordtxt.getText().toString();
        if(username.trim().isEmpty() || password.trim().isEmpty())
        {
            if(username.trim().isEmpty())
            {
                usernametxt.requestFocus();
                return;
            }
            if(password.trim().isEmpty())
            {
                passwordtxt.requestFocus();
                return;
            }
        }else {
            if(false!=checkConnection()){
                LoadingBar = new ProgressDialog(MainActivity.this);
                LoadingBar.setMessage("Loading...");LoadingBar.setCanceledOnTouchOutside(false);LoadingBar.show();

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<String>call = apiService.getLoginResponse(username,password);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            if (response.body().equals("1")){

                                errorshow.setText("Found");
                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, AdminBashBoard_Activity.class);
                                startActivity(intent);

                            }else if (response.body().equals("0")){
                                Toast.makeText(MainActivity.this, "Not Match",Toast.LENGTH_SHORT).show();
                                errorshow.setText("Not Match");
                            }else {
                                Toast.makeText(MainActivity.this, "Something Wrong!",Toast.LENGTH_SHORT).show();
                                errorshow.setText("Something Wrong!");
                            }
                        }
                        response.body();
                        LoadingBar.cancel();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        }
    }

    public boolean checkConnection()
    {
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork== null)
        {
            Toast.makeText(MainActivity.this, "No Internet Connection",Toast.LENGTH_SHORT).show();
            return false;

        }else{
            return true;
        }
    }
}
