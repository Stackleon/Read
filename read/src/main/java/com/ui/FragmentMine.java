package com.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.read.R;

import org.json.JSONException;
import org.json.JSONObject;


import bean.CleanableEditText;

/**
 * Created by Administrator on 2016/4/11.
 */
public class FragmentMine extends Fragment {

    private Fragment fragment;

    private Button bt_login,bt_register;
    private CleanableEditText et_username,et_password;
    private View view;

    private String path = "http://api.manyanger.com:8101/manyanger/login.htm?username=%d&password=%d";
    private RequestQueue mQueue;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_mine,null);
        mQueue = Volley.newRequestQueue(getContext());
        init();
        return view;
    }

    private void init(){
        bt_login = (Button) view.findViewById(R.id.button_login);
        bt_register = (Button) view.findViewById(R.id.button_register);
        et_username = (CleanableEditText) view.findViewById(R.id.username);
        et_password = (CleanableEditText) view.findViewById(R.id.password);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                path = String.format(path,username,password);
                StringRequest stringRequest = new StringRequest(path,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jo = new JSONObject(response);
                                    if(jo.getString("BackMessage").equals("成功")){

                                    }else{
                                        Toast.makeText(getContext(), "用户名或者密码错误", Toast.LENGTH_SHORT).show();

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
        });



    }

}
