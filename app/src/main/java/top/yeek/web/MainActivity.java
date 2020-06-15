package top.yeek.web;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    private EditText mEdit = null;
    private Button mButton = null;
    private WebView mWeb = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        mEdit = (EditText)findViewById(R.id.myEdit1);
        mButton = (Button)findViewById(R.id.myButton1);
        mWeb = (WebView)findViewById(R.id.myWeb1);

        mWeb.getSettings().setJavaScriptEnabled(true);
        //mWeb.getSettings().setPluginsEnabled(true);
        mWeb.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        mButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //String strUrl = mEdit.getText().toString();
                String strUrl = "https://wwww.baidu.com";
                mWeb.loadUrl(strUrl);
            }
        });
    }

    private void getStaticPageByBytes(String surl, String strFile){

        Log.i("getStaticPageByBytes", surl + ", " + strFile);

        HttpURLConnection connection = null;
        InputStream is = null;

        File file = new File(strFile);
        FileOutputStream fos = null;

        try {
            URL url = new URL(surl);
            connection = (HttpURLConnection)url.openConnection();

            int code = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == code) {
                connection.connect();
                is = connection.getInputStream();
                fos = new FileOutputStream(file);

                int i;
                while((i = is.read()) != -1){
                    fos.write(i);
                }

                is.close();
                fos.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}