package com.fytu.basemvp.model;


/**
 *
 */
public class SimpleModelImpl implements ISimpleModel {


    @Override
    public void loadSimple(SimpleOnLoadListener loadListener) {
        String result = "your content";
        loadListener.onComplete(result); // 通过onComplete方法把结果回调到上一层去
    }
}
