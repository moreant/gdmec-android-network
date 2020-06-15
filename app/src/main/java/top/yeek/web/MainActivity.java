package top.yeek.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button mButton1;
    private TextView mTextView1;
    private ImageView mImageView1;
    String uriPic = "https://www.baidu.com/img/baidu_sylogo1.gif";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        mButton1 = (Button) findViewById(R.id.myButton1);
        mTextView1 = (TextView) findViewById(R.id.myTextView1);
        mImageView1 = (ImageView) findViewById(R.id.myImageView1);

        mButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /* 设定Bitmap于ImageView中 */
                mImageView1.setImageBitmap(getURLBitmap());
                mTextView1.setText("");

            }
        });
    }

    public Bitmap getURLBitmap() {
        URL imageUrl = null;
        Bitmap bitmap = null;
        try {
            /* new URL对象将网址传入 */
            imageUrl = new URL(uriPic);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            /* 取得联机 */
            HttpURLConnection conn = (HttpURLConnection) imageUrl
                    .openConnection();
            conn.connect();
            /* 取得回传的InputStream */
            InputStream is = conn.getInputStream();
            /* 将InputStream变成Bitmap */
            bitmap = BitmapFactory.decodeStream(is);
            /* 关闭InputStream */
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
