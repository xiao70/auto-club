package com.sinelead.car.club.map;

import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.sinelead.car.club.R;
import com.sinelead.car.club.constant.Constant;

public class NaviMainActivity extends FragmentActivity implements
		OnMapLongClickListener, InfoWindowAdapter, OnMapClickListener,
		OnMarkerClickListener, OnCameraChangeListener
{

	static final Integer[] imagelist = { R.drawable.ft_loc_normal,
			R.drawable.main_icon_follow, R.drawable.main_icon_compass };

	private MapView mMapView;
	private AMap mAmap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	private MyLocationStyle myLocationStyle;
	private AMapEx aMapEx;
	private ImageSwitcher imageswitcher;
	private ImageView imageCompass;

	private int currentLocationType = AMap.LOCATION_TYPE_LOCATE;

	private boolean rotateCur = false;

	private float defalutZoom = 15;

	private GeocodeSearch geocoderSearch;

	private MapMarker mapMarker;

	private PoiOverlay poiOverlay;
	private NaviMainFragmentSearch SearchTopFragment;

	private Marker focusMarker;

	private NaviMarkerFragmentBottom markerBottomFragment;

	private String searchKey;

	private SupportMapFragment mMapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_map);

		init(savedInstanceState);

	}

	private void init(Bundle savedInstanceState)
	{
		aMapEx = new AMapEx(this);

		mAmap = aMapEx.getAmap();
		setUpMap();

		if (findViewById(R.id.layout_bottom) != null)
		{

			if (savedInstanceState != null)
			{
				return;
			}

			NaviMainFragmentBottom firstFragment = new NaviMainFragmentBottom();

			firstFragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
					.add(R.id.layout_bottom, firstFragment).commit();
		}

		if (findViewById(R.id.layout_Top) != null)
		{

			if (savedInstanceState != null)
			{
				return;
			}

			SearchTopFragment = new NaviMainFragmentSearch();

			SearchTopFragment.setArguments(getIntent().getExtras());

			getSupportFragmentManager().beginTransaction()
					.add(R.id.layout_Top, SearchTopFragment).commit();
		}

	}

	private void setUpMap()
	{

		aMapEx.setCameraListener(this);
		mAmap.setOnMapLongClickListener(this);
		mAmap.setOnMapClickListener(this);
		// 暂时不用marker窗口的自定义
		mAmap.setInfoWindowAdapter(this);
		mAmap.setOnMarkerClickListener(this);// 添加点击marker监听事件
	}

	private void searchResult(Intent intent)
	{
		// 判断是不是搜索界面调整过来的
		if (intent.getAction() != null
				&& intent.getAction().equalsIgnoreCase("poi"))
		{
			List<PoiItem> poiItems = intent.getExtras().getParcelableArrayList(
					"poi");
			searchKey = intent.getExtras().getString("key");

			poiOverlay = new PoiOverlay(mAmap, poiItems)
			{

				@Override
				protected BitmapDescriptor getBitmapDescriptor(int index)
				{
					return getSearchMarker(index);
				}

			};

			poiOverlay.removeFromMap();
			poiOverlay.addToMap();
			poiOverlay.zoomToSpan();
			SearchTopFragment.setSearchText(searchKey);

		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{

		super.onNewIntent(intent);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent)
	{
		switch (resultCode)
		{
		case Constant.RESULT_SEARCH:
			searchResult(intent);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// mMapView.onResume();

	}

	protected void onPause()
	{
		super.onPause();
		// mMapView.onPause();

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// mMapView.onDestroy();
		aMapEx.deactivate();
		// 对当前位置进行保存下次启动自动定位到这个位置

	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		// mMapView.onSaveInstanceState(outState);
	}

	public void clearPoiOverlay()
	{
		restoreBottom();
		poiOverlay.removeFromMap();
		poiOverlay = null;
		focusMarker = null;
		SearchTopFragment.setSearchText(null);
		// 返回到搜索框
		SearchTopFragment.gotoSearchActivity(searchKey);
	}

	@Override
	public void onBackPressed()
	{

		if (mapMarker != null)
		{
			restoreBottom();
			mapMarker.remove();
			mapMarker = null;
		}
		if (poiOverlay != null)
		{
			clearPoiOverlay();
		}
		else
		{
			super.onBackPressed();
		}
		return;
	}

	@Override
	public void onCameraChange(CameraPosition arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onCameraChangeFinish(CameraPosition arg0)
	{
		// TODO Auto-generated method stub
		if (focusMarker == null && mAmap.getMapScreenMarkers() != null
				&& !mAmap.getMapScreenMarkers().isEmpty())
		{
			onMarkerClick(mAmap.getMapScreenMarkers().get(0));
		}
	}

	@Override
	public void onMapClick(LatLng arg0)
	{
		// 地图标记的辅助类
		if (mapMarker != null)
		{
			restoreBottom();
			mapMarker.remove();
			mapMarker = null;
		}

	}

	// 长按地图绘制marker
	@Override
	public void onMapLongClick(LatLng arg0)
	{
		if (mapMarker != null)
		{
			mapMarker.remove();
			mapMarker = null;
		}
		mapMarker = new MapMarker(this, mAmap, arg0);
		replaceBottom(null);
		resetSearchMarker();
	}

	@Override
	public View getInfoContents(Marker arg0)
	{

		return null;
	}

	@Override
	public View getInfoWindow(Marker marker)
	{
		// View infoWindow = getLayoutInflater().inflate(
		// R.layout.custom_info_window, null);
		//
		// render(marker, infoWindow);
		// return infoWindow;
		return null;
	}

	public void render(Marker marker, View view)
	{

	}

	private BitmapDescriptor getSearchMarker(int index)
	{
		BitmapDescriptor bitmapDescriptor = null;
		switch (index)
		{
		case 0:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marka);
			break;
		case 1:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markb);
			break;
		case 2:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markc);
			break;
		case 3:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markd);
			break;
		case 4:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marke);
			break;
		case 5:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markf);
			break;
		case 6:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markg);
			break;
		case 7:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markh);
			break;
		case 8:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marki);
			break;
		case 9:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_markj);
			break;
		default:
			break;
		}
		return bitmapDescriptor;
	}

	private BitmapDescriptor getSearchFocusMarker(int index)
	{
		BitmapDescriptor bitmapDescriptor = null;
		switch (index)
		{
		case 0:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_marka);
			break;
		case 1:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markb);
			break;
		case 2:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markc);
			break;
		case 3:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markd);
			break;
		case 4:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_marke);
			break;
		case 5:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markf);
			break;
		case 6:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markg);
			break;
		case 7:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markh);
			break;
		case 8:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_marki);
			break;
		case 9:
			bitmapDescriptor = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_focus_markj);
			break;
		default:
			break;
		}
		return bitmapDescriptor;
	}

	public void resetSearchMarker()
	{
		if (focusMarker != null && poiOverlay != null)
		{
			focusMarker.setIcon(getSearchMarker(poiOverlay
					.getPoiIndex(focusMarker)));
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		// TODO Auto-generated method stub
		if (poiOverlay != null)
		{
			int index = poiOverlay.getPoiIndex(marker); // 本地蓝点得到是-1
			if (getSearchFocusMarker(index) == null)
			{
				return true;
			}
			resetSearchMarker();

			marker.setIcon(getSearchFocusMarker(index));
			focusMarker = marker;

			mAmap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					marker.getPosition(), mAmap.getCameraPosition().zoom),
					2000, null);
			mAmap.animateCamera(CameraUpdateFactory.changeLatLng(marker
					.getPosition()));
			// 下发显示导航条

			replaceBottom(poiOverlay.getPoiItem(index));
			// markerBottomFragment.setValues(poiOverlay.getPoiItem(index));
			if (mapMarker != null)
			{
				mapMarker.remove();
				mapMarker = null;
			}
		}
		return true;
	}

	protected void replaceBottom(PoiItem poiItem)
	{

		markerBottomFragment = new NaviMarkerFragmentBottom();
		Bundle bundle = new Bundle();
		if (poiItem != null)
		{
			bundle.putParcelable("poiItem", poiItem);
			markerBottomFragment.setArguments(bundle);
		}

		markerBottomFragment.setArguments(bundle);
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction
				.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
		fragmentTransaction.replace(R.id.layout_bottom, markerBottomFragment);
		// fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
		// fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	protected void restoreBottom()
	{
		markerBottomFragment = null;

		NaviMainFragmentBottom firstFragment = new NaviMainFragmentBottom();

		firstFragment.setArguments(getIntent().getExtras());

		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction
				.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
		fragmentTransaction.replace(R.id.layout_bottom, firstFragment);
		// fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
		// fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	public NaviMarkerFragmentBottom getMarkerBottomFragment()
	{
		return markerBottomFragment;
	}

	public Marker getCurrentMarker()
	{
		if (mapMarker != null)
		{
			return mapMarker.getMarker();
		}
		return focusMarker;
	}

}
