package com.mintegral.sdk.demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.MIntegralSDK;
import com.mintegral.msdk.MIntegralUser;
import com.mintegral.msdk.out.MIntegralSDKFactory;
import com.mintegral.msdk.out.MTGRewardVideoHandler;
import com.mintegral.msdk.out.RewardVideoListener;
import com.mintegral.msdk.videocommon.download.NetStateOnReceive;
import com.mintegral.sdk.demo.util.SystemBarTintManager;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.util.Map;

public class RewardActivity extends UnityPlayerActivity {
	private final static String TAG = "RewardActivity";
	private MTGRewardVideoHandler mMTGRewardVideoHandler;
	private NetStateOnReceive mNetStateOnReceive;
	private String mRewardUnitId = "21310";
	private String mRewardId = "12817";
	private String mUserId = "123";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        onCreate();
        initData();

//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//        		Start();
//			}
//		},5);

    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintColor(0x2ba4c2 // getResources().getColor(R.color.mintegral_demo_blue)
        );
        tintManager.setStatusBarTintEnabled(true);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

	public void initData() {
	    onCreate();
		initHandler();

		mMTGRewardVideoHandler.load();
	}

	public void Start()
	{
        if (mMTGRewardVideoHandler.isReady()) {
            mMTGRewardVideoHandler.show(mRewardId, mUserId);
        }
	}

	private void initHandler() {
		try {
			// Declare network status for downloading video
			if (mNetStateOnReceive == null) {
				mNetStateOnReceive = new NetStateOnReceive();
				IntentFilter filter = new IntentFilter();
				filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
				registerReceiver(mNetStateOnReceive, filter);
			}

			mMTGRewardVideoHandler = new MTGRewardVideoHandler(this, mRewardUnitId);
			mMTGRewardVideoHandler.setRewardVideoListener(new RewardVideoListener() {

				@Override
				public void onLoadSuccess(String unitId) {
					Log.e(TAG, "onLoadSuccess:"+Thread.currentThread());
					Toast.makeText(getApplicationContext(), "onLoadSuccess()", Toast.LENGTH_SHORT).show();

				}

				@Override
				public void onVideoLoadSuccess(String unitId) {
					Log.e(TAG, "onVideoLoadSuccess:"+Thread.currentThread());
					Toast.makeText(getApplicationContext(), "onVideoLoadSuccess()", Toast.LENGTH_SHORT).show();

//	        		Start();
				}

				@Override
				public void onVideoLoadFail(String errorMsg) {
					Log.e(TAG, "onVideoLoadFail errorMsg:"+errorMsg);
					Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onShowFail(String errorMsg) {
					Log.e(TAG, "onShowFail=" + errorMsg);
					Toast.makeText(getApplicationContext(), "errorMsg:" + errorMsg, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onAdShow() {
					Log.e(TAG, "onAdShow");
					Toast.makeText(getApplicationContext(), "onAdShow", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onAdClose(boolean isCompleteView, String RewardName, float RewardAmout) {
					Log.e(TAG, "onAdClose rewardinfo :" + "RewardName:" + RewardName + "RewardAmout:" + RewardAmout+" isCompleteView："+isCompleteView);
					if(isCompleteView){
						Toast.makeText(getApplicationContext(),"onADClose:"+isCompleteView+",rName:"+RewardName +"，RewardAmout:"+RewardAmout,Toast.LENGTH_SHORT).show();
						UnityPlayer.UnitySendMessage("Canvas", "ReceiveAndroidMessage",RewardName + "," + RewardAmout);
					}else{
						Toast.makeText(getApplicationContext(),"onADClose:"+isCompleteView+",rName:"+RewardName +"，RewardAmout:"+RewardAmout,Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onVideoAdClicked(String unitId) {
					Log.e(TAG, "onVideoAdClicked");
					Toast.makeText(getApplicationContext(), "onVideoAdClicked", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onVideoComplete(String unitId) {
					Log.e(TAG, "onVideoComplete");
					Toast.makeText(getApplicationContext(), "onVideoComplete", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onEndcardShow(String unitId) {
					Log.e(TAG, "onEndcardShow");
					Toast.makeText(getApplicationContext(), "onEndcardShow", Toast.LENGTH_SHORT).show();
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (mNetStateOnReceive != null) {
				unregisterReceiver(mNetStateOnReceive);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// move from MainActivity
    public void onCreate() {

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
