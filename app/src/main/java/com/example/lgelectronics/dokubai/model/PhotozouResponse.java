package com.example.lgelectronics.dokubai.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LG Electronics on 2017/08/20.
 */

public class PhotozouResponse {
    //json최상위 객체
    @SerializedName("info")
    private PhotozouResponseInfo info;

    public PhotozouResponseInfo getInfo() {
        return info;
    }

    /*public void setInfo(PhotozouResponseInfo info) {
        this.info = info;
    }
*/
}
