package com.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.read.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MainBookListAdapter;
import bean.Books;
import util.ParserJson;

public class ClassifyActivity extends Activity {

    private TextView tv_title;
    private ImageView iv_search, iv_back;
    private ListView listview;

    private String path = "http://api.manyanger.com:8101/novel/novelList.htm?pageNo=%d&theme=";
    private int id;
    private RequestQueue mQueue;
    private List<Books> list;
    private int currentPage = 0;
    private MainBookListAdapter adapter;

    private int visibleLastIndex = 0;   //最后的可视项索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        initView();
        mQueue = Volley.newRequestQueue(this);
        tv_title.setText(getIntent().getExtras().getString("title"));
        id = getIntent().getExtras().getInt("ID");
        path = path + id;
        downLoadJson();
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int itemsLastIndex = adapter.getCount() - 1;    //数据集最后一项的索引
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == itemsLastIndex) {
                    //如果是自动加载,可以在这里放置异步加载数据的代码
                    currentPage++;
                    addView();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", list.get(position).getId());
                intent.putExtras(bundle);
                intent.setClass(ClassifyActivity.this, DetailActivity.class);
                ClassifyActivity.this.startActivity(intent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassifyActivity.this.finish();
            }
        });

    }

    public void initView() {
        tv_title = (TextView) findViewById(R.id.title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        listview = (ListView) findViewById(R.id.listiew);
        list = new ArrayList<>();
        iv_search.setVisibility(View.GONE);
    }

    private void downLoadJson() {
        StringRequest stringRequest = new StringRequest(String.format(path, currentPage),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        list.addAll(ParserJson.parserClassifyJson(response));
                        adapter = new MainBookListAdapter(ClassifyActivity.this, list);
                        listview.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ClassifyActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
        );
        mQueue.add(stringRequest);

    }

    private void addView() {
        StringRequest stringRequest = new StringRequest(String.format(path, currentPage),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        list.addAll(ParserJson.parserClassifyJson(response));
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ClassifyActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
            }
        }
        );
        mQueue.add(stringRequest);

    }

}
