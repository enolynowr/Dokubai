package com.example.lgelectronics.dokubai;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.lgelectronics.dokubai.api.ApiService;
import com.example.lgelectronics.dokubai.api.ApiServiceImpl;
import com.example.lgelectronics.dokubai.model.PhotozouResponse;
import com.example.lgelectronics.dokubai.model.PhotozouResponseInfo;
import com.example.lgelectronics.dokubai.view.ConvertBitmapAsync;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "@@@@@_MainActivity_TAG";
    public static ProgressDialog mProgressDialog;
    EditText et_keyword;
    Button btn_search;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Photo Viewer");

        et_keyword = (EditText) findViewById(R.id.edittext_keyword);
        btn_search = (Button) findViewById(R.id.btn_search);
        gv = (GridView) findViewById(R.id.gridview_photo);

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");

        btn_search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_keyword = et_keyword.getText().toString();
                if (input_keyword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter keyword", Toast.LENGTH_LONG).show();
                    return;
                }
                setThumbnailImages(input_keyword);
            }
        });
    }

    public void setThumbnailImages(String keyword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        //@@@@@ Network Check
        if (mobile.isConnected() || wifi.isConnected()) {
            mProgressDialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_LONG).show();
            return;
        }

        //api통신
        ApiServiceImpl apiServiceImpl = new ApiServiceImpl();
        ApiService apiService = apiServiceImpl.getApiService();
        Call<PhotozouResponse> call = apiService.getPhotozou(keyword);

        call.enqueue(new Callback<PhotozouResponse>() {
            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<PhotozouResponse> call, Response<PhotozouResponse> response) {

                if (!response.isSuccessful()) {
                    Log.d(LOG_TAG, "API Response Fail");
                    Toast.makeText(getApplicationContext(), "API Response Fail", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d(LOG_TAG, "API Response Success");
                PhotozouResponse res = response.body();
                PhotozouResponseInfo photos = res.getInfo();
                ConvertBitmapAsync cba = new ConvertBitmapAsync(MainActivity.this);
                cba.execute(photos.getPhoto());
                //TextView textView = (TextView) findViewById(R.id.api_test);
                //textView.setText(resInfo.getPhoto().get(0).getImage_url());
            }
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<PhotozouResponse> call, Throwable t) {
                Log.d(LOG_TAG, "API Request Fail");
                Toast.makeText(getApplicationContext(), "API Request Fail", Toast.LENGTH_LONG).show();
            }
        });
    }
}
