package com.example.cou.Model;

import com.google.gson.annotations.SerializedName;

public class ShowAllCoupons {

    @SerializedName("id")
    private String Id;

    @SerializedName("codetitle")
    private String CodeTitle;

    @SerializedName("code")
    private String Code;

    @SerializedName("status")
    private String Status;

    public String getId() {
        return Id;
    }

    public String getCodeTitle() {
        return CodeTitle;
    }

    public String getCode() {
        return Code;
    }

    public String getStatus() {
        return Status;
    }
}
