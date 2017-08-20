package com.example.lgelectronics.dokubai.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.lgelectronics.dokubai.R;

public class ImageActivity extends AppCompatActivity {

    ImageView iv_big_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        iv_big_photo = (ImageView)findViewById(R.id.iv_big_photo);
        setImage(iv_big_photo);
    }

    private void setImage(ImageView iv){

        Intent receiveIntent = getIntent();

        Bitmap imgBitmap = (Bitmap) receiveIntent.getExtras().get("imageBitmap");
        iv.setImageBitmap(imgBitmap);

    }
}
