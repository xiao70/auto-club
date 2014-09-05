package com.sinelead.car.club.map;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.trinea.android.common.util.ToastUtils;

import com.amap.api.maps.model.Marker;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.sinelead.car.club.ClubApplication;
import com.sinelead.car.club.R;

public class NaviMarkerFragmentBottom extends Fragment

{
	private View mView;
	private View naviBotton;
	private NaviLatLng currentlatLng;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mView = inflater.inflate(R.layout.fragment_marker_bottom, container,
				false);
		init();
		return mView;
	}

	private void init()
	{

		getCurrentlatlng();

		Bundle bundle = this.getArguments();

		if (bundle != null)
		{
			PoiItem poiItem = (PoiItem) bundle.getParcelable("poiItem");
			setValues(poiItem);
		}

		naviBotton = mView.findViewById(R.id.layout_nav);
		naviBotton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);// 设置声音控制
				TTSController ttsManager = TTSController
						.getInstance(getActivity());// 初始化语音模块
				ttsManager.init();
				AMapNavi aMapNavi = AMapNavi.getInstance(getActivity());
				aMapNavi.setAMapNaviListener(ttsManager);// 设置语音模块播报

				if (aMapNavi.calculateDriveRoute(getStartPoints(),
						getEndPoints(), null, AMapNavi.DrivingDefault))
				{

					Intent naviIntent = new Intent(getActivity(),
							NaviCustomActivity.class);
					startActivity(naviIntent);
				}
				else
				{
					ToastUtils.show(getActivity(), "路径计算失败，请在网络良好情况下重试!");
				}
			}

		});
	}

	private NaviLatLng getLocation()
	{
		Location lastLocation = ClubApplication.aLocation;

		NaviLatLng nav = new NaviLatLng();
		nav.setLatitude(lastLocation.getLatitude());
		nav.setLongitude(lastLocation.getLongitude());

		return nav;
	}

	private void getCurrentlatlng()
	{
		currentlatLng = new NaviLatLng();
		Marker marker = ((NaviMainActivity) getActivity()).getCurrentMarker();
		currentlatLng.setLatitude(marker.getPosition().latitude);
		currentlatLng.setLongitude(marker.getPosition().longitude);
	}

	private List<NaviLatLng> getStartPoints()
	{
		List<NaviLatLng> startPoints = new ArrayList<NaviLatLng>();
		NaviLatLng nav = getLocation();
		startPoints.add(nav);
		return startPoints;
	}

	private List<NaviLatLng> getEndPoints()
	{
		List<NaviLatLng> endPoints = new ArrayList<NaviLatLng>();
		endPoints.add(currentlatLng);
		return endPoints;
	}

	public void setValues(RegeocodeResult geo)
	{
		try
		{
			if (geo != null && geo.getRegeocodeAddress() != null)

			{
				RegeocodeAddress address = geo.getRegeocodeAddress();
				String location = address.getFormatAddress();

				TextView textView = (TextView) mView
						.findViewById(R.id.textView_location);
				textView.setText(location);

				textView = (TextView) mView.findViewById(R.id.textView_nearby);
				String nearby = address.getPois().get(0).getTitle();
				textView.setText(nearby + "附近");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void setValues(PoiItem poiItem)
	{
		try
		{
			{
				TextView textView = (TextView) mView
						.findViewById(R.id.textView_location);
				textView.setText(poiItem.getTitle());

				textView = (TextView) mView.findViewById(R.id.textView_nearby);
				String nearby = poiItem.getSnippet();
				textView.setText(nearby);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
