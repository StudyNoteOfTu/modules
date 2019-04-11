package com.fytu.basemvp.view;


/**
 * 定义出所有的ui逻辑
 */
public interface ISimpleView {

    void loadSimple();

    //使用回调的方式返回数据
    void showSimple(String simpleStr);


}
