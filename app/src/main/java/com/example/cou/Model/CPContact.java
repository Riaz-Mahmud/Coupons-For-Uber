package com.example.cou.Model;

import com.google.gson.annotations.SerializedName;

public class CPContact {

    @SerializedName("id")
    private String Id;

    @SerializedName("codetitle")
    private String CodeTitle;

    @SerializedName("code")
    private String Code;

    @SerializedName("uses")
    private String Uses;

    @SerializedName("saveds")
    private String Saveds;


    public String getId() {
        return Id;
    }

    public String getCodeTitle() {
        return CodeTitle;
    }

    public String getCode() {
        return Code;
    }

    public String getUses() {
        return Uses;
    }

    public String getSaveds() {
        return Saveds;
    }
}
