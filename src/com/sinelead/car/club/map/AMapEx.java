package com.sinelead.car.club.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.sinelead.car.club.ClubApplication;
import com.sinelead.car.club.R;

public class AMapEx implements LocationSource, OnCameraChangeListener,
		ViewFactory, AMapLocationListener
{
	private Context context;

	private SensorManager mSensorManager;
	private Sensor mOrientationSensor;
	private float angle;

	static final Integer[] imagelist = { R.drawable.ft_loc_normal,
			R.drawable.main_icon_follow, R.drawable.main_icon_compass };

	private AMap mAmap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private MyLocationStyle myLocationStyle;

	private ImageSwitcher imageswitcher;
	private ImageView imageCompass;

	private int currentLocationType = AMap.LOCATION_TYPE_LOCATE;

	private boolean rotateCur = false;

	private float defalutZoom = 15;

	private OnCameraChangeListener cameraListener;

	public AMapEx(Context context)
	{
		this.context = context;

		init();

	}

	private void init()
	{
		setUpMap();
		setUpSwitch();
		setUpCompass();
		setUpTraffic();
		setUpSensor();
	}

	public AMap getAmap()
	{
		return mAmap;
	}

	private void setUpMap()
	{
		mAmap = ((SupportMapFragment) ((FragmentActivity) context)
				.getSupportFragmentManager().findFragmentById(R.id.map))
				.getMap();
		myLocationStyle = new MyLocationStyle();
		myLocationStyle.myLocationIcon(BitmapDescriptorFactory
				.fromResource(R.drawable.location_marker));
		myLocationStyle.strokeColor(Color.GRAY);
		myLocationStyle.radiusFillColor(R.color.AlphaDodgerBlue); // 蓝色透明的
		myLocationStyle.strokeWidth(1);
		mAmap.setMyLocationStyle(myLocationStyle);
		
		mAmap.getUiSettings().setScaleControlsEnabled(true);

		mAmap.getUiSettings().setCompassEnabled(false); // 不显示指南针
		mAmap.setLocationSource(this);

		mAmap.setMyLocationEnabled(true);
		mAmap.getUiSettings().setMyLocationButtonEnabled(false);

		mAmap.setMyLocationType(currentLocationType);

		rotateCur = true;

		// 调整视野
		CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(defalutZoom);
		mAmap.moveCamera(cameraUpdate);
	}

	private void setUpTraffic()
	{
		ImageButton imageTraffic = (ImageButton) ((Activity) context)
				.findViewById(R.id.imageTraffic);
		imageTraffic.setOnClickListener(new OnClickListener()
		{

			private boolean traffic = false;

			@Override
			public void onClick(View v)
			{
				ImageButton imageTraffic = (ImageButton) v;
				if (traffic == false)
				{
					traffic = true;
					imageTraffic
							.setBackgroundResource(R.drawable.map_traffic_hl);
					mAmap.setTrafficEnabled(true);
				}
				else
				{
					traffic = false;
					imageTraffic.setBackgroundResource(R.drawable.map_traffic);
					mAmap.setTrafficEnabled(false);
				}
			}

		});
	}

	private void setUpCompass()
	{
		imageCompass = (ImageView) ((Activity) context)
				.findViewById(R.id.imageCompass);
		mAmap.setOnCameraChangeListener(this);
	}

	private void moveToLocation()
	{
		Location lastLocation = mAmap.getMyLocation();
		if (lastLocation != null)
		{
			LatLng lastLatIng = new LatLng(lastLocation.getLatitude(),
					lastLocation.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory
					.changeLatLng(lastLatIng);
			mAmap.moveCamera(cameraUpdate);

		}
	}

	private void setUpSwitch()
	{
		imageswitcher = (ImageSwitcher) ((Activity) context)
				.findViewById(R.id.imageSwitcher);
		imageswitcher.setFactory(this);
		imageswitcher.setImageResource(imagelist[0]);
		imageswitcher.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (currentLocationType == AMap.LOCATION_TYPE_LOCATE)
				{
					moveToLocation();
					currentLocationType = AMap.LOCATION_TYPE_MAP_FOLLOW;
					mAmap.setMyLocationType(currentLocationType);
					imageswitcher.setImageResource(imagelist[1]);
					rotateCur = true;

				}
				else if (currentLocationType == AMap.LOCATION_TYPE_MAP_FOLLOW)
				{
					moveToLocation();
					currentLocationType = AMap.LOCATION_TYPE_MAP_ROTATE;
					mAmap.setMyLocationType(currentLocationType);
					imageswitcher.setImageResource(imagelist[2]);
					rotateCur = false;

				}
				else if (currentLocationType == AMap.LOCATION_TYPE_MAP_ROTATE)
				{
					moveToLocation();
					currentLocationType = AMap.LOCATION_TYPE_LOCATE;
					mAmap.setMyLocationType(currentLocationType);
					imageswitcher.setImageResource(imagelist[0]);
					rotateCur = true;

				}
			}

		});
	}

	private void setUpSensor()
	{
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		mOrientationSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION);

		angle = 0;

		setRotateWithSensor(true);
	}

	public float getAngle()
	{
		return angle;
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.what == 1)
			{
				if (rotateCur)
				{
					float angleOrientation = (Float) msg.obj;
					moveCur(angleOrientation);
				}
			}
			// CameraPosition position = aMap.getCameraPosition();
			// aMap.moveCamera(CameraUpdateFactory
			// .newCameraPosition(CameraPosition.builder().target(position.target).tilt(position.tilt).zoom(position.zoom)
			// .bearing((Float) msg.obj).build()));
			// aMap.moveCamera(CameraUpdateFactory.changeBearing((Float)
			// msg.obj));
		}
	};

	public void moveCur(float angleOrientation)
	{
		float bearing = mAmap.getCameraPosition().bearing;
		bearing = (angleOrientation + bearing) % 360;
		mAmap.setMyLocationRotateAngle(bearing);
	}

	public void setRotateWithSensor(boolean enabled)
	{
		if (mOrientationSensor == null)
			return;
		if (enabled)
		{
			mSensorManager.registerListener(mSensorEventListener,
					mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
		}
		else
		{
			mSensorManager.unregisterListener(mSensorEventListener);
		}
	}

	private SensorEventListener mSensorEventListener = new SensorEventListener()
	{
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy)
		{

		}

		@Override
		public void onSensorChanged(SensorEvent event)
		{
			if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
			{
				float x = event.values[SensorManager.DATA_X];
				float orientation = (360 - x) % 360;
				if (Math.abs(orientation - angle) > 1)
				{
					angle = orientation;
					Message msg = handler.obtainMessage(1, orientation);
					msg.sendToTarget();
				}
			}

		}
	};

	@Override
	public void activate(OnLocationChangedListener listener)
	{
		mListener = listener;
		if (mAMapLocationManager == null)
		{
			mAMapLocationManager = LocationManagerProxy.getInstance(context);

			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 5000, 10, this);
		}
	}

	public void deactivate()
	{
		mListener = null;
		if (mAMapLocationManager != null)
		{
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onCameraChange(CameraPosition position)
	{

		if (position.bearing > 1)
		{
			float bearing = (360 - position.bearing) % 360;
			// 指南针旋转

			imageCompass.setRotation(bearing);
		}
		if (cameraListener != null)
		{
			cameraListener.onCameraChange(position);
		}
	}

	@Override
	public void onCameraChangeFinish(CameraPosition position)
	{
		// TODO Auto-generated method stub
		if (cameraListener != null)
		{
			cameraListener.onCameraChangeFinish(position);
		}
	}

	public void setCameraListener(OnCameraChangeListener cameraListener)
	{
		this.cameraListener = cameraListener;
	}

	@Override
	public View makeView()
	{
		ImageView iv = new ImageView(context);
		iv.setScaleType(ImageView.ScaleType.CENTER);
		iv.setLayoutParams(new ImageSwitcher.LayoutParams(
				ImageSwitcher.LayoutParams.MATCH_PARENT,
				ImageSwitcher.LayoutParams.MATCH_PARENT));
		return iv;
	}

	@Override
	public void onLocationChanged(AMapLocation aLocation)
	{
		if (mListener != null && aLocation != null
				&& aLocation.getAMapException().getErrorCode() == 0)
		{

			ClubApplication.aLocation = aLocation;
			// setRotateAngle(float rotate)
			mListener.onLocationChanged(aLocation);

			if (!aLocation.hasBearing() && rotateCur)
			{
				rotateCur = true;
				moveCur(getAngle());
			}
			else
			{
				rotateCur = false;

			}
		}
		else
		{
			rotateCur = true;
			moveCur(getAngle());
		}
	}

	@Override
	public void onLocationChanged(Location location)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub

	}
}
