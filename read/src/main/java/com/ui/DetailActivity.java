package com.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.read.R;

import adapter.CatalogAdapter;
import bean.BookDetail;
import util.BitmapCache;
import util.ParserJson;
import util.SetListHeight;

public class DetailActivity extends Activity {

    private TextView tv_auth, tv_theme, tv_process, tv_price, tv_depict,tv_title;
    private ImageView iv;
    private ListView catalog;
    private Button bt_favor, bt_buy;

    private String path = "http://api.manyanger.com:8101/novel/novelDetail.htm?id=";
    private int id;
    private RequestQueue mQueue;

    private BookDetail bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);
        mQueue = Volley.newRequestQueue(this);
        id = getIntent().getExtras().getInt("ID");
        Log.i("12312312313",id + "");
        path = path + id;
        initView();
        downloadJson();
    }

    private void initView() {
        tv_auth = (TextView) findViewById(R.id.tv_auth);
        tv_theme = (TextView) findViewById(R.id.tv_theme);
        tv_process = (TextView) findViewById(R.id.tv_process);
        tv_depict = (TextView) findViewById(R.id.depict);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv = (ImageView) findViewById(R.id.imageview);
        catalog = (ListView) findViewById(R.id.catlog);
        bt_favor = (Button) findViewById(R.id.favor_btn);
        bt_buy = (Button) findViewById(R.id.buy_btn);
    }

    private void downloadJson() {
        StringRequest stringRequest = new StringRequest(path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        bd = ParserJson.parserJson(response);
                        tv_theme.setText(bd.getTheme());
                        tv_process.setText(bd.getWords() + "字(" + bd.getProcess() + ")");
                        tv_depict.setText(bd.getDepict());
                        tv_auth.setText(bd.getAuthor());
                        tv_title.setText(bd.getTitle());
                        downloadImg();
                        catalog.setAdapter(new CatalogAdapter(bd.getBooklist(), DetailActivity.this));
                        SetListHeight.setListViewHeight(catalog);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(DetailActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);

    }

    private void downloadImg(){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv,
                R.drawable.default_big_icon, R.drawable.default_big_icon);
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        imageLoader.get(bd.getCover(), listener);
    }

}
