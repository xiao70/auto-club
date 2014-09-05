package com.sinelead.car.club.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapNaviLocation;
import com.sinelead.car.club.R;

/**
 * ʵʱ��������
 * 
 */
public class NaviCustomActivity extends Activity implements
		AMapNaviViewListener {

	private AMapNaviView mAmapAMapNaviView;
	// �����������õĲ���
	private boolean mDayNightFlag = Utils.DAY_MODE;// Ĭ��Ϊ����ģʽ
	private boolean mDeviationFlag = Utils.YES_MODE;// Ĭ�Ͻ���ƫ������
	private boolean mJamFlag = Utils.YES_MODE;// Ĭ�Ͻ���ӵ������
	private boolean mTrafficFlag = Utils.OPEN_MODE;// Ĭ�Ͻ��н�ͨ����
	private boolean mCameraFlag = Utils.OPEN_MODE;// Ĭ�Ͻ�������ͷ����
	private boolean mScreenFlag = Utils.YES_MODE;// Ĭ������Ļ����
	// ����������
	private int mThemeStle = AMapNaviViewOptions.BLUE_COLOR_TOPIC;
	// ��������
	private AMapNaviListener mAmapNaviListener;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navicustom);
		// ʵʱ������ʽ���е���
		AMapNavi.getInstance(this).startNavi(AMapNavi.GPSNaviMode);
		initView(savedInstanceState);
	}

	private void initView(Bundle savedInstanceState) {
		mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.customnavimap);
		mAmapAMapNaviView.onCreate(savedInstanceState);
		mAmapAMapNaviView.setAMapNaviViewListener(this);
		setAmapNaviViewOptions();
	}

	/**
	 * ���õ����Ĳ���
	 */
	private void setAmapNaviViewOptions() {
		if (mAmapAMapNaviView == null) {
			return;
		}
		AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
		viewOptions.setSettingMenuEnabled(false);// ���õ���setting������
		viewOptions.setNaviNight(mDayNightFlag);// ���õ����Ƿ�Ϊ��ҹģʽ
		viewOptions.setReCalculateRouteForYaw(mDeviationFlag);// ���õ�ƫ���Ƿ�����
		viewOptions.setReCalculateRouteForTrafficJam(mJamFlag);// ���ý�ͨӵ���Ƿ�����
		viewOptions.setTrafficInfoUpdateEnabled(mTrafficFlag);// �����Ƿ����·��
		viewOptions.setCameraInfoUpdateEnabled(mCameraFlag);// ��������ͷ����
		viewOptions.setScreenAlwaysBright(mScreenFlag);// ������Ļ�������
		viewOptions.setNaviViewTopic(mThemeStle);// ���õ�������������ʽ

		mAmapAMapNaviView.setViewOptions(viewOptions);

	}

	private AMapNaviListener getAMapNaviListener() {
		if (mAmapNaviListener == null) {

			mAmapNaviListener = new AMapNaviListener() {

				@Override
				public void onTrafficStatusUpdate() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onStartNavi(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onReCalculateRouteForYaw() {
					// ������Ƶ������ʱ��������,�������֮��
					// i++;
					// if (i >= 5) {
					// AMapNaviViewOptions viewOptions = new
					// AMapNaviViewOptions();
					// viewOptions.setReCalculateRouteForYaw(false);
					// mAmapAMapNaviView.setViewOptions(viewOptions);
					// }
				}

				@Override
				public void onReCalculateRouteForTrafficJam() {

				}

				@Override
				public void onLocationChange(AMapNaviLocation location) {

				}

				@Override
				public void onInitNaviSuccess() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onInitNaviFailure() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onGetNavigationText(int arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onEndEmulatorNavi() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onCalculateRouteSuccess() {

				}

				@Override
				public void onCalculateRouteFailure(int arg0) {

				}

				@Override
				public void onArrivedWayPoint(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onArriveDestination() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onGpsOpenStatus(boolean arg0) {
					// TODO Auto-generated method stub

				}
			};
		}
		return mAmapNaviListener;
	}

	// -------����
	/**
	 * �������淵�ذ�ť����
	 * */
	@Override
	public void onNaviCancel() {
		Intent intent = new Intent(NaviCustomActivity.this,
				NaviMainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}

	/**
	 * ������ð�ť���¼�
	 */
	@Override
	public void onNaviSetting() {
//		Bundle bundle = new Bundle();
//		bundle.putInt(Utils.THEME, mThemeStle);
//		bundle.putBoolean(Utils.DAY_NIGHT_MODE, mDayNightFlag);
//		bundle.putBoolean(Utils.DEVIATION, mDeviationFlag);
//		bundle.putBoolean(Utils.JAM, mJamFlag);
//		bundle.putBoolean(Utils.TRAFFIC, mTrafficFlag);
//		bundle.putBoolean(Utils.CAMERA, mCameraFlag);
//		bundle.putBoolean(Utils.SCREEN, mScreenFlag);
//		Intent intent = new Intent(NaviCustomActivity.this,
//				NaviSettingActivity.class);
//		intent.putExtras(bundle);
//		startActivity(intent);

	}

	@Override
	public void onNaviMapMode(int arg0) {

	}

	private void processBundle(Bundle bundle) {

		if (bundle != null) {
			mDayNightFlag = bundle.getBoolean(Utils.DAY_NIGHT_MODE,
					mDayNightFlag);
			mDeviationFlag = bundle.getBoolean(Utils.DEVIATION, mDeviationFlag);
			mJamFlag = bundle.getBoolean(Utils.JAM, mJamFlag);
			mTrafficFlag = bundle.getBoolean(Utils.TRAFFIC, mTrafficFlag);
			mCameraFlag = bundle.getBoolean(Utils.CAMERA, mCameraFlag);
			mScreenFlag = bundle.getBoolean(Utils.SCREEN, mScreenFlag);
			mThemeStle = bundle.getInt(Utils.THEME);

		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(NaviCustomActivity.this,
					NaviMainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	// ------------------------------�������ڷ���---------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAmapAMapNaviView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Bundle bundle = getIntent().getExtras();
		processBundle(bundle);
		setAmapNaviViewOptions();
		AMapNavi.getInstance(this).setAMapNaviListener(getAMapNaviListener());
		mAmapAMapNaviView.onResume();

	}

	@Override
	public void onPause() {
		mAmapAMapNaviView.onPause();
		super.onPause();
		AMapNavi.getInstance(this)
				.removeAMapNaviListener(getAMapNaviListener());

	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		mAmapAMapNaviView.onDestroy();
	}

}