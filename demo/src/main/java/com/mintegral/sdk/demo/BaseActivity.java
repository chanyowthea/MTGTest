//package com.mintegral.sdk.demo;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.content.ContextCompat;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.mintegral.sdk.demo.util.SystemBarTintManager;
//import com.unity3d.player.UnityPlayerActivity;
//
//
///**
// * all Activity extends base class
// */
//public abstract class BaseActivity extends UnityPlayerActivity {
//    @SuppressLint("ResourceAsColor")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(getResLayoutId());
//        initWindow();
//        initView();
//        initData();
//        setListener();
//
//
//    }
//
//
//    public abstract int getResLayoutId();
//
//    public abstract void initView();
//
//    public abstract void initData();
//
//    public abstract void setListener();
//
//}
