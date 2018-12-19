package com.bwei.erjiliandong.persenter;



import com.bwei.erjiliandong.callback.MyCallBack;
import com.bwei.erjiliandong.model.IModelImpl;
import com.bwei.erjiliandong.view.IView;

import java.util.Map;

public class IPersenterImpl implements IPersenter {

    private IModelImpl iModel;
    private IView mIView;

    public IPersenterImpl(IView iView){
        mIView=iView;
        iModel=new IModelImpl();
    }

    @Override
    public void getRequest(String dataUrl, Map<String, String> params, Class clazz) {
        iModel.getRequest(dataUrl, params, clazz, new MyCallBack() {
            @Override
            public void onCallBack(Object data) {
                mIView.onSuccess(data);
            }
        });
    }
    public void deteach(){
        iModel=null;
        mIView=null;
    }
}
