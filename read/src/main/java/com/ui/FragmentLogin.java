package com.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.util.zip.Inflater;

import bean.CleanableEditText;
import bean.LoginMessage;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/4/12.
 */
public class FragmentLogin extends Fragment {

    private View view;
    private LinearLayout ll_login,ll_unlogin;
    private Button bt_login,bt_regist,bt_order;
    private TextView tv_username;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine,null);
        EventBus.getDefault().register(this);
        initView();
        ll_unlogin.setVisibility(View.VISIBLE);
        return view;
    }

    private void initView(){
        ll_login = (LinearLayout) view.findViewById(R.id.layout_logined);
        ll_unlogin = (LinearLayout) view.findViewById(R.id.layout_unlogin);
        bt_login = (Button) view.findViewById(R.id.login_button);
        bt_regist = (Button) view.findViewById(R.id.regist_button);
        tv_username = (TextView) view.findViewById(R.id.tv_username);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(LoginMessage event) {
        tv_username.setText(event.getUsername());
        ll_login.setVisibility(View.VISIBLE);
        ll_unlogin.setVisibility(View.GONE);

    }
}












