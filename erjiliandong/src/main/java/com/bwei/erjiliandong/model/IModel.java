package com.bwei.erjiliandong.model;


import com.bwei.erjiliandong.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void getRequest(String dataUrl, Map<String, String> params, Class clazz, MyCallBack callBack);
}
