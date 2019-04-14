package com.mintegral.sdk.demo;

import android.app.Application;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.MIntegralUser;
import com.mintegral.msdk.out.MIntegralSDKFactory;

import java.util.Map;

public class MainApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

//		if (LeakCanary.isInAnalyzerProcess(this)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}

		MIntegralConstans.DEBUG = true;

//		LeakCanary.install(this);
//		//检查ANR工具
//		BlockCanary.install(this, new BlockCanaryContext()).start();

		Log.i("demo", "application oncreate");

        reportUser(33.0121,22.001);
		MIntegralConstans.DEBUG = true;

		// init sdk
		final MIntegralSDK sdk = MIntegralSDKFactory.getMIntegralSDK();
		// test appId and appKey
		String appId = "92762";
		String appKey = "936dcbdd57fe235fd7cf61c2e93da3c4";
		Map<String, String> map = sdk.getMTGConfigurationMap(appId, appKey);
		// if you modify applicationId, please add the following attributes,
		// otherwise it will crash
		// map.put(MIntegralConstans.PACKAGE_NAME_MANIFEST, "your AndroidManifest
		// package value");

		sdk.init(map, this);
		//update user
        reportUser(111.0121,-15.001);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		DisplayMetrics dm = this.getBaseContext().getResources().getDisplayMetrics();
		this.getBaseContext().createConfigurationContext(newConfig);
	}

    /**
     * report current user info,this can Raise Revenue
     */
	public static void reportUser(double lat , double lng){
        MIntegralUser mIntegralUser = new MIntegralUser();
        // 1(pay),0(no pay)，if unkonw you can not set
        mIntegralUser.setPay(1);
        // 1male,2fmale(int类型),if unkonw you can not set
        mIntegralUser.setGender(2);
        // set current user age,if unkonw you can not set
        mIntegralUser.setAge(28);
        //Custom parameters
        mIntegralUser.setCustom("Custom parameters");
        //set user longitude,if unkonw you can not set
        mIntegralUser.setLng(lng);
        //set user latitude,if unkonw you can not set
        mIntegralUser.setLat(lat);
        MIntegralSDKFactory.getMIntegralSDK().reportUser(mIntegralUser);
    }





}
