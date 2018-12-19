package com.bwei.gouwuche.okhttp;


import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static OkHttpUtils instance;

    private Handler mHandler=new Handler(Looper.getMainLooper());
    private final OkHttpClient client;

    public static OkHttpUtils getInstance() {
        if(instance==null){
            synchronized (OkHttpUtils.class){
                if(null==instance){
                    instance=new OkHttpUtils();
                }
            }
        }
        return instance;
    }
    public OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    public void getRequeue(String dataUrl, Map<String,String> param, final Class clazz, final ICallBack callBack){
        Request builder = new Request.Builder()
                .get()
                .url(dataUrl)
                .build();
        Call call = client.newCall(builder);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final Object o = new Gson().fromJson(string, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponce(o);
                    }
                });
            }
        });
    }


    public void postRequeue(String dataUrl, Map<String,String> param, final Class clazz, final ICallBack callBack){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry:param.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody build = builder.build();
        Request builder1 = new Request.Builder()
                .post(build)
                .url(dataUrl)
                .build();
        Call call = client.newCall(builder1);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final Object o = new Gson().fromJson(string, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onResponce(o);
                    }
                });
            }
        });
    }

}
