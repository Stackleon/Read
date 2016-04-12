package com.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.read.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.ShelfGridViewAdapter;
import bean.CleanableEditText;
import bean.MyEvent;
import bean.ShelfBook;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/12.
 */
public class FragmentShelf extends Fragment {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private View view;
    private GridView gv;
    private LinearLayout ll_shelf;

//    private ImageView iv_back, iv_search, search_search, search_back;
//    private TextView title;
//    private CleanableEditText edittext;
//    private LinearLayout ll_search;
//    private RelativeLayout rl_layout;

    private List<String> list_id;
    private RequestQueue mQueue;
    private String path = "http://api.manyanger.com:8101/novel/novelDetail.htm?id=";
    private List<ShelfBook> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = getActivity().getSharedPreferences("shelf", Context.MODE_PRIVATE);
        editor=preferences.edit();
        view = inflater.inflate(R.layout.fragmentshelf, null);
        initView();
        list_id = new ArrayList<>();
        mQueue = Volley.newRequestQueue(getContext());
        EventBus.getDefault().register(this);
        findData();
        return view;
    }

    public void onEventMainThread(MyEvent event) {
        findData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void findData(){
        list_id.clear();
        list.clear();
        Map<String,?> map = preferences.getAll();
        for (String key : map.keySet()) {
            list_id.add((String)map.get(key));
        }
        if(map.size() != 0){
            ll_shelf.setVisibility(View.GONE);
            gv.setVisibility(View.VISIBLE);
        }else{
            ll_shelf.setVisibility(View.VISIBLE);
            gv.setVisibility(View.GONE);
        }
        for (int i = 0; i < list_id.size() ; i ++){
            getData(path + list_id.get(i));
        }
    }

    public void initView(){
//        title = (TextView) view.findViewById(R.id.title);
//        title.setText("本地书架");
//        iv_back = (ImageView) view.findViewById(R.id.iv_back);
//        iv_search = (ImageView) view.findViewById(R.id.iv_search);
//        iv_back.setVisibility(View.GONE);
//
//        search_back = (ImageView) view.findViewById(R.id.search_back);
//        edittext = (CleanableEditText) view.findViewById(R.id.search_edittext);
//        search_search = (ImageView) view.findViewById(R.id.search_search);
//        ll_search = (LinearLayout) view.findViewById(R.id.search_layout);
//        rl_layout = (RelativeLayout) view.findViewById(R.id.layout);
//        iv_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_search);
//                rl_layout.setVisibility(View.GONE);
//                ll_search.setVisibility(View.VISIBLE);
//                ll_search.setAnimation(animation);
//                animation.startNow();
//            }
//        });
//
//        search_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                rl_layout.setVisibility(View.VISIBLE);
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_search_out);
//                ll_search.setAnimation(animation);
//                animation.startNow();
//                animation.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        ll_search.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//
//            }
//        });
//        search_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), SearchActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("search", edittext.getText().toString());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        ll_shelf = (LinearLayout) view.findViewById(R.id.ll_shelf);
        gv = (GridView) view.findViewById(R.id.gridView);
        list = new ArrayList<>();
    }

    private void getData(String path){
        StringRequest stringRequest = new StringRequest(path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ShelfBook sBook = new ShelfBook();
                            JSONObject jo = new JSONObject(response);
                            sBook.setCover(jo.getJSONObject("novel").getString("cover"));
                            sBook.setTitle(jo.getJSONObject("novel").getString("title"));
                            sBook.setId(jo.getJSONObject("novel").getInt("id"));
                            list.add(sBook);
                            if(list.size() == list_id.size()){
                                gv.setAdapter(new ShelfGridViewAdapter(getContext(),list));
                                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent();
                                        intent.setClass(getActivity(), DetailActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("ID", list.get(position).getId());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);
    }


}
