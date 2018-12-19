package com.bwei.erjiliandong.persenter;

import java.util.Map;

public interface IPersenter {
    void getRequest(String dataUrl, Map<String, String> params, Class clazz);
}
