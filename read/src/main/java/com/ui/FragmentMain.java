package com.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.read.MainActivity;
import com.example.administrator.read.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.MainBookListAdapter;
import bean.Banner;
import bean.Books;
import util.BitmapCache;
import util.ParserJson;
import util.SetListHeight;

/**
 * Created by Administrator on 2016/4/8.
 */
public class FragmentMain extends Fragment implements View.OnClickListener{

    private View view;
    private ListView lv_recommend, lv_competitive, lv_update;
    private Button bt_recommend, bt_competitive, bt_update;
    private String path = "http://api.manyanger.com:8101/novel/index.htm";
    private String banner_url = "http://api.manyanger.com:8101/novel/getAd.htm?type=Bookshelf&num=5";
    private RequestQueue mQueue;
    private List<Books> list_recommend, list_competitive, list_update;
    private ViewPager viewpager;
    private List<ImageView> list_iv;
    private List<Banner> list_banner;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                sendEmptyMessageDelayed(0, 3000);
            }
        }

        ;
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentmain, null);
        init();
        initView();
        mQueue = Volley.newRequestQueue(getContext());
        downloadJson();
        handler.sendEmptyMessageDelayed(0, 3000);
        return view;
    }

    private void initView() {
        lv_recommend = (ListView) view.findViewById(R.id.listview_recommend);
        lv_competitive = (ListView) view.findViewById(R.id.listview_competitive);
        lv_update = (ListView) view.findViewById(R.id.listview_update);
        bt_recommend = (Button) view.findViewById(R.id.bt_recommend);
        bt_competitive = (Button) view.findViewById(R.id.bt_competitive);
        bt_update = (Button) view.findViewById(R.id.bt_update);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        bt_recommend.setOnClickListener(this);
        bt_competitive.setOnClickListener(this);
        bt_update.setOnClickListener(this);
//        setViewPagerAdapter();

    }

    private void init() {
        list_recommend = new ArrayList<>();
        list_competitive = new ArrayList<>();
        list_update = new ArrayList<>();
        list_iv = new ArrayList<>();
        list_banner = new ArrayList<>();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_recommend:

                break;
            case R.id.bt_competitive:
                break;
            case R.id.bt_update:
                break;
            default:
                break;

        }
    }

    private void downloadJson() {
        StringRequest stringRequest = new StringRequest(path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        list_recommend.addAll(ParserJson.parserJson(response, "recommendNovelList"));
                        list_competitive.addAll(ParserJson.parserJson(response, "featuredNovelList"));
                        list_update.addAll(ParserJson.parserJson(response, "latestNovelList"));
                        lv_recommend.setAdapter(new MainBookListAdapter(getContext(), list_recommend));
                        lv_competitive.setAdapter(new MainBookListAdapter(getContext(), list_competitive));
                        lv_update.setAdapter(new MainBookListAdapter(getContext(), list_update));
                        SetListHeight.setListViewHeight(lv_update);
                        SetListHeight.setListViewHeight(lv_competitive);
                        SetListHeight.setListViewHeight(lv_recommend);
                        lv_competitive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putInt("ID",list_competitive.get(position).getId());
                                intent.putExtras(bundle);
                                intent.setClass(getContext(),DetailActivity.class);
                                getContext().startActivity(intent);
                            }
                        });
                        lv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putInt("ID",list_recommend.get(position).getId());
                                intent.putExtras(bundle);
                                intent.setClass(getContext(),DetailActivity.class);
                                getContext().startActivity(intent);
                            }
                        });
                        lv_update.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putInt("ID",list_update.get(position).getId());
                                intent.putExtras(bundle);
                                intent.setClass(getContext(),DetailActivity.class);
                                getContext().startActivity(intent);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "未知错误", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(stringRequest);

    }



    public void setViewPagerAdapter() {

        final RequestQueue mQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(banner_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ja = new JSONObject(response).getJSONArray("adList");
                            for (int i = 0; i < ja.length(); i++) {
                                Banner banner = new Banner();
                                banner.setImage(ja.getJSONObject(i).getString("image"));
                                banner.setUrl(ja.getJSONObject(i).getString("url"));
                                list_banner.add(banner);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        List<ImageView> list_cache = new ArrayList<>();
                        List<ImageView> list_cache1 = new ArrayList<>();
                        for (int i = 0; i < list_banner.size(); i++) {
                            final int count = i;
                            ImageView iv = new ImageView(getContext());
                            iv.setScaleType(ImageView.ScaleType.FIT_XY);
                            ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv,
                                    R.drawable.default_big_icon, R.drawable.default_big_icon);
                            ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
                            imageLoader.get(list_banner.get(i).getImage(), listener);
                            iv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = null;
                                    Uri uri = Uri.parse(list_banner.get(count).getUrl());
                                    intent = new Intent(Intent.ACTION_VIEW, uri);
                                    getContext().startActivity(intent);
                                }
                            });
                            list_cache.add(iv);
                            if (i == list_banner.size() - 1) {
                                list_iv.add(iv);
                            } else if (i == 0) {
                                list_cache1.add(iv);
                            }
                        }
                        list_iv.addAll(list_cache);
                        list_iv.addAll(list_cache1);
                        viewpager.setAdapter(new PagerAdapter() {


                            @Override
                            public void destroyItem(ViewGroup container, int position,
                                                    Object object) {
                                // TODO Auto-generated method stub
                                ImageView view = list_iv.get(position);
                                ((ViewPager) container).removeView(view);
                            }

                            @Override
                            public Object instantiateItem(ViewGroup container, int position) {
                                // TODO Auto-generated method stub
                                ((ViewPager) container).addView(list_iv.get(position));
                                return list_iv.get(position);
                            }

                            @Override
                            public boolean isViewFromObject(View arg0, Object arg1) {
                                // TODO Auto-generated method stub
                                return arg0 == arg1;
                            }

                            @Override
                            public int getCount() {
                                // TODO Auto-generated method stub
                                return list_iv.size();
                            }


                        });

                        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                if (position == list_iv.size() - 1) {
                                    viewpager.setCurrentItem(0);
                                }
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
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
