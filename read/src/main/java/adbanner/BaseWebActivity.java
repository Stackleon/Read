package adbanner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ProgressBar;

import com.example.administrator.read.R;


/**
 * @Description:WebView界面，带自定义进度条显示
 */
public class BaseWebActivity extends Activity {

    protected ProgressWebView mWebView;
    private ProgressBar web_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseweb);

        mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        initData();
    }

    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("url");

        // if(!TextUtils.isEmpty(url)&&TextUtils.isEmpty(title)){
        mWebView.loadUrl(url);

        // }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mWebView = null;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
