package com.freshly_built.ravi.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Wave;

public class MainActivity extends AppCompatActivity {
    private WebView mywebView;
    public ProgressBar progressBar;



    //attaching main activity to Freshly_Built
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            Toast.makeText(MainActivity.this,"Loading Fresh Content", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
            progressBar=(ProgressBar)findViewById(R.id.progressBar1);
            mywebView=(WebView)findViewById(R.id.webView);
            WebSettings webSettings=mywebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            mywebView.loadUrl("http://freshlybuilt.com");
            mywebView.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(WebView.VISIBLE);
                }



                @Override
                public void onPageFinished(WebView view, String url)
                {
                    progressBar.setVisibility(WebView.GONE);
                    super.onPageFinished(view, url);

                }
            });
        }
    }




    //back button
    @Override
    public void onBackPressed()
    {
        if(mywebView.canGoBack())
        { mywebView.goBack(); }
        else
        { super.onBackPressed(); }
    }


    //internet_connectivity_error_methods
    public boolean isConnected(Context context)
    {
        final ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23)
            {
                final NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null)
                {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            }
            else
            {
                final Network n = cm.getActiveNetwork();

                if (n != null)
                {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }
        return false;
    }


    public AlertDialog.Builder buildDialog(Context c)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                finish();
            }
        });

        return builder;
    }
}
