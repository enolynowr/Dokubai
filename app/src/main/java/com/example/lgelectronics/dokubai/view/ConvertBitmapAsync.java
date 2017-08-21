package com.example.lgelectronics.dokubai.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.GridView;

import com.example.lgelectronics.dokubai.MainActivity;
import com.example.lgelectronics.dokubai.R;
import com.example.lgelectronics.dokubai.model.PhotozouResponseInfoPhoto;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by LG Electronics on 2017/08/20.
 */
// 사진 url을 비트맵으로 변환
public class ConvertBitmapAsync extends AsyncTask<List<PhotozouResponseInfoPhoto>,Void,Bitmap[]> {
    //private final String LOG_TAG = "@@@@@_ConvertBitmap_TAG";
    Activity activity;//Main Activity
    GridView gv;
    //Constructor
    public ConvertBitmapAsync(Activity _activity) {
        this.activity = _activity;
        gv = (GridView) activity.findViewById(R.id.gridview_photo);
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        //MainActivity.mProgressDialog.show();
    }

    @Override
    protected Bitmap[] doInBackground(List<PhotozouResponseInfoPhoto>... params) {

        List<PhotozouResponseInfoPhoto> list_prip = params[0];
        final int length = list_prip.size();
        final Bitmap[] bitmap = new Bitmap[length];
        HttpURLConnection connection = null;

        try{
            for(int i=0; i<length; i++){
                URL imgUrl = new URL(list_prip.get(i).getImage_url());
                connection = (HttpURLConnection) imgUrl.openConnection();
                connection.setDoInput(true); //url로 input받는 flag 허용
                connection.connect(); //연결
                InputStream is = connection.getInputStream(); // get inputstream
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap[i] = BitmapFactory.decodeStream(is, null, options);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap[] resImgBitmap){
        super.onPostExecute(resImgBitmap);

        GridViewAdapter adapter = new GridViewAdapter(
                activity.getApplicationContext()//Main Activity
                , R.layout.row
                , resImgBitmap);
        gv.setAdapter(adapter);

        if(MainActivity.mProgressDialog.isShowing()){
            MainActivity.mProgressDialog.dismiss();
        }
    }
}
