package com.example.lgelectronics.dokubai.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG Electronics on 2017/08/20.
 */

public class PhotozouResponseInfo {

    @SerializedName("photo_num")
    private String photo_num;

    @SerializedName("photo")
    private List<PhotozouResponseInfoPhoto> photo = new ArrayList<>();

    public String getPhoto_num() {
        return photo_num;
    }

    public void setPhoto_num(String photo_num) {
        this.photo_num = photo_num;
    }

    public List<PhotozouResponseInfoPhoto> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotozouResponseInfoPhoto> photo) {
        this.photo = photo;
    }
}
