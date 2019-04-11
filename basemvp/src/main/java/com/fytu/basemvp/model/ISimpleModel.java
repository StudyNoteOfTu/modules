package com.fytu.basemvp.model;


/**
 * 用来加载数据
 */
public interface ISimpleModel {

    void loadSimple(SimpleOnLoadListener articleOnLoadListener);

    //设计一个内部回调接口，返回获得的数据
    interface SimpleOnLoadListener{
        void onComplete(String result);//参数是回调回来的参数
    }
}
