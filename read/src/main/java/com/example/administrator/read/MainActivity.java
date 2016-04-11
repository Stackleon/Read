package com.example.administrator.read;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.ui.FragmentClassify;
import com.ui.FragmentMain;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView ll_main, ll_bookshelf, ll_classify, ll_more;
    private ViewPager vp;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        initView();
        ll_main.performClick();

    }


    private void initView() {
        ll_main = (ImageView) findViewById(R.id.item_main);
        ll_bookshelf = (ImageView) findViewById(R.id.item_bookshelf);
        ll_classify = (ImageView) findViewById(R.id.item_classify);
        ll_more = (ImageView) findViewById(R.id.item_more);
        vp = (ViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<>();
        list.add(new FragmentMain());
        list.add(new FragmentMain());
        list.add(new FragmentClassify());
        list.add(new FragmentMain());
        ll_main.setOnClickListener(this);
        ll_bookshelf.setOnClickListener(this);
        ll_classify.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        vp.setAdapter(new adapter.PagerAdapter(getSupportFragmentManager(), list));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ll_main.performClick();
                        break;
                    case 1:
                        ll_bookshelf.performClick();
                        break;
                    case 2:
                        ll_classify.performClick();
                        break;
                    case 3:
                        ll_more.performClick();
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }


        });
        vp.setOffscreenPageLimit(4);
    }

    @Override
    public void onClick(View v) {
        reset();
        switch (v.getId()) {
            case R.id.item_main:
                vp.setCurrentItem(0);
                ll_main.setImageResource(R.drawable.tab1_2);
                break;
            case R.id.item_bookshelf:
                vp.setCurrentItem(1);
                ll_bookshelf.setImageResource(R.drawable.tab2_2);
                break;
            case R.id.item_classify:
                vp.setCurrentItem(2);
                ll_classify.setImageResource(R.drawable.tab3_2);
                break;
            case R.id.item_more:
                vp.setCurrentItem(3);
                ll_more.setImageResource(R.drawable.tab4_2);
                break;
            default:
                break;
        }


    }

    public void reset() {
        ll_main.setImageResource(R.drawable.tab1_1);
        ll_bookshelf.setImageResource(R.drawable.tab2_1);
        ll_classify.setImageResource(R.drawable.tab3_1);
        ll_more.setImageResource(R.drawable.tab4_1);
    }
}
