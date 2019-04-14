package com.mintegral.sdk.demo;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mintegral.msdk.MIntegralConstans;
import com.mintegral.msdk.out.MTGRewardVideoHandler;
import com.mintegral.msdk.out.RewardVideoListener;
import com.mintegral.msdk.videocommon.download.NetStateOnReceive;
//import com.mintegral.sdk.demo.view.CommonTitleLayout;

public class RewardActivity extends BaseActivity implements OnClickListener {
	private final static String TAG = "RewardActivity";
	private Button bt_load;
	private Button bt_show;
	private Button bt_mute;
	private Button bt_unmute;
//	private CommonTitleLayout mTitleLayout;
	private MTGRewardVideoHandler mMTGRewardVideoHandler;
	private ProgressBar mProgressBar;
	private NetStateOnReceive mNetStateOnReceive;
	private Dialog mDialog;
	private String mRewardUnitId = "21310";
	private String mRewardId = "12817";
	private String mUserId = "123";

	@Override
	public int getResLayoutId() {
		return R.layout.mintegral_demo_atv_reward;
	}

	@Override
	public void initView() {
		bt_load = (Button) findViewById(R.id.bt_load);
		bt_show = (Button) findViewById(R.id.bt_show);
		bt_mute = (Button) findViewById(R.id.bt_mute);
		bt_unmute = (Button) findViewById(R.id.bt_unmute);
		mProgressBar = (ProgressBar) findViewById(R.id.mintegral_demo_progress);
//		mTitleLayout = (CommonTitleLayout) findViewById(R.id.mintegral_demo_common_title_layout);
	}

	@Override
	public void initData() {
		initHandler();

	}

	@Override
	public void setListener() {
//		mTitleLayout.setTitleText("RewardVideo");
		bt_load.setOnClickListener(this);
		bt_show.setOnClickListener(this);
		bt_mute.setOnClickListener(this);
		bt_unmute.setOnClickListener(this);
	}

	private void showLoadding() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	private void hideLoadding() {
		mProgressBar.setVisibility(View.GONE);
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
					hideLoadding();
					Toast.makeText(getApplicationContext(), "onVideoLoadSuccess()", Toast.LENGTH_SHORT).show();

				}

				@Override
				public void onVideoLoadFail(String errorMsg) {
					Log.e(TAG, "onVideoLoadFail errorMsg:"+errorMsg);
					hideLoadding();
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
						showDialog(RewardName, RewardAmout);
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
	public void onClick(View v) {
		if (v.getId() == R.id.bt_load)
		{
			showLoadding();
			mMTGRewardVideoHandler.load();
		}
		else if(v.getId() == R.id.bt_show)
		{
			if (mMTGRewardVideoHandler.isReady()) {
				mMTGRewardVideoHandler.show(mRewardId, mUserId);
			}
		}

//		switch (v.getId()) {
//			case R.id.bt_load:
//				showLoadding();
//				mMTGRewardVideoHandler.load();
//				break;
//			case R.id.bt_show:
//				if (mMTGRewardVideoHandler.isReady()) {
//					mMTGRewardVideoHandler.show(mRewardId, mUserId);
//				}
//				break;
//			case R.id.bt_mute:
//				if (mMTGRewardVideoHandler != null) {
//					Toast.makeText(getApplicationContext(),"bt_mute",Toast.LENGTH_SHORT).show();
//					mMTGRewardVideoHandler.playVideoMute(MIntegralConstans.REWARD_VIDEO_PLAY_MUTE);
//				}
//				break;
//			case R.id.bt_unmute:
//				if (mMTGRewardVideoHandler != null) {
//					Toast.makeText(getApplicationContext(),"bt_unmute",Toast.LENGTH_SHORT).show();
//					mMTGRewardVideoHandler.playVideoMute(MIntegralConstans.REWARD_VIDEO_PLAY_NOT_MUTE);
//				}
//				break;
//		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (mDialog != null) {
				mDialog = null;
			}
			if (mNetStateOnReceive != null) {
				unregisterReceiver(mNetStateOnReceive);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showDialog(String RewardName, float RewardAmout) {
		try {
//			mDialog = new Dialog(RewardActivity.this);
//			View view = View.inflate(RewardActivity.this, R.layout.dialog_reward, null);
//			TextView tvRewardName = (TextView) view.findViewById(R.id.tv_rewardName);
//			TextView tvRewardAmout = (TextView) view.findViewById(R.id.tv_RewardAmout);
//			tvRewardName.setText(RewardName + "");
//			tvRewardAmout.setText(RewardAmout + "");
//			mDialog.setContentView(view);
//			mDialog.show();
		}catch (Throwable t){
			t.printStackTrace();
		}
	}





}
