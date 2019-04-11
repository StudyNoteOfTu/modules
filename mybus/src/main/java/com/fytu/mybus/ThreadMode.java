package com.fytu.mybus;

public enum ThreadMode {
    /**
     * 主线程对主线程，子线程对子线程，也就是 发布者和订阅者一定要在同一种线程内
     */
    POSTING,
    /**
     * 主-主，子-主
     */
    MAIN,
    /**
     * 主-子，子-子
     */
    BACKGROUND,
    /**
     * 异步线程
     */
    ASYNC
}
