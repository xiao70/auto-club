/*
*
*	版权所有(C) 2014 xiao70 <196245957@qq.com>
*   
*
*	本程序为自由软件；您可依据自由软件基金会所发表的GNU通用公共授权条款，对本程序再次发布或修改；无论您依据的是本授权的第三版，或（您可选的）任一日后发行的版本。
*	本程序是基于使用目的而加以发布，然而不负任何担保责任；亦无对适售性或特定目的适用性所为的默示性担保。详情请参照GNU通用公共授权。
*	您应已收到附随于本程序的GNU通用公共授权的副本；如果没有，请参照
*	<http://www.gnu.org/licenses/>.
* 	如果您项目引用本文件代码或本项目代码，请保留作者、版权和许可通知，如果您程序为商业目的请在程序交互界面，关于信息中保留作者，版权等信息。
*
*	This program is free software; you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation; either version 3 of the License, or
*	(at your option) any later version.
*      
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*	GNU General Public License for more details.
*      
*	You should have received a copy of the GNU General Public License
*	along with this program; if not, write to the Free Software
*	Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
*	MA 02110-1301, USA.
*/
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
