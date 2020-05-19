package com.example.cou.Model;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cou.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.List;


public class CPRecyclerAdapter extends RecyclerView.Adapter<CPRecyclerAdapter.MyViewHolder>{

    private List<CPContact> contacts;
    private Context mcontext;
    ViewGroup prnt;
    private InterstitialAd interstitialAd;

    TextView rateustxt,copyCodetxt, couponTitleTxt;

    public CPRecyclerAdapter(List<CPContact> contacts, Context context)
    {
        this.contacts = contacts;
        this.mcontext =  context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.prnt= parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.codeTitle.setText(contacts.get(position).getCodeTitle());
        holder.code.setText(contacts.get(position).getCode());
        holder.usesTxtShowCode.setText(contacts.get(position).getUses()+" Uses");
        holder.savedTxtShowCode.setText(contacts.get(position).getSaveds() + " Saved");



        holder.codeShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.barcodeImg.setVisibility(View.GONE);
                holder.code.setVisibility(View.VISIBLE);


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mcontext).setCancelable(true);
                final View mView = LayoutInflater.from(prnt.getContext()).inflate(R.layout.cupon_copy_dialog,null);
                rateustxt = mView.findViewById(R.id.rateustxt);
                copyCodetxt = mView.findViewById(R.id.copyCodetxt);
                couponTitleTxt = mView.findViewById(R.id.couponTitleTxt);

                couponTitleTxt.setText(contacts.get(position).getCodeTitle());

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        holder.barcodeImg.setVisibility(View.VISIBLE);
                        holder.code.setVisibility(View.GONE);
                    }
                });

                copyCodetxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                            ClipboardManager clipboardManager = (ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("EditText", contacts.get(position).getCode());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(mcontext, "Coupon Copied",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            holder.barcodeImg.setVisibility(View.VISIBLE);
                            holder.code.setVisibility(View.GONE);
                        }else {
                            ClipboardManager clipboardManager = (ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText("EditText", contacts.get(position).getCode());
                            clipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(mcontext, "Coupon Copied",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            holder.barcodeImg.setVisibility(View.VISIBLE);
                            holder.code.setVisibility(View.GONE);
                        }

                    }
                });
                rateustxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mcontext.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=" + "com.android.chrome")));
                        }catch (ActivityNotFoundException e){
                            mcontext.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=" + "com.android.chrome"))); //mcontext.getPackageName()
                        }
                        dialog.dismiss();
                        holder.barcodeImg.setVisibility(View.VISIBLE);
                        holder.code.setVisibility(View.GONE);
                    }
                });

            }
        });

    }

    void showDialog(){

    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView codeTitle, code, usesTxtShowCode, savedTxtShowCode;
        Button codeShowBtn;
        ImageView barcodeImg;

        public MyViewHolder( View itemView) {
            super(itemView);
            codeTitle = itemView.findViewById(R.id.codeTitle);
            code = itemView.findViewById(R.id.code);
            barcodeImg = itemView.findViewById(R.id.barcodeImg);
            codeShowBtn = itemView.findViewById(R.id.codeShowBtn);
            usesTxtShowCode = itemView.findViewById(R.id.usesTxtShowCode);
            savedTxtShowCode = itemView.findViewById(R.id.savedTxtShowCode);

            //Admob
            MobileAds.initialize(mcontext,"ca-app-pub-2699591493465867~5559600553");
            interstitialAd = new InterstitialAd(mcontext);
            interstitialAd.setAdUnitId("ca-app-pub-2699591493465867/5191741371");
            interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        }
    }





}
