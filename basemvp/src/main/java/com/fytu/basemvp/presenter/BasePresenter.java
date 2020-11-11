package com.fytu.basemvp.presenter;

import java.lang.ref.WeakReference;

public class BasePresenter<T> {

    //解决内存泄漏问题，我们暂时采用弱引用方式
    protected WeakReference<T> mViewRef;

    //根除内存泄漏 --- 绑定
    //进行绑定
    public void attachView(T view){
        mViewRef = new WeakReference<T>(view);
    }
    //进行解绑
    public void deattachView(){
        //手动地打断，而非让gc来
        mViewRef.clear();
    }


}
