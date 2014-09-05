/*
*
*	��Ȩ����(C) 2014 xiao70 <196245957@qq.com>
*   
*
*	������Ϊ������������������������������������GNUͨ�ù�����Ȩ����Ա������ٴη������޸ģ����������ݵ��Ǳ���Ȩ�ĵ����棬������ѡ�ģ���һ�պ��еİ汾��
*	�������ǻ���ʹ��Ŀ�Ķ����Է�����Ȼ�������κε������Σ����޶������Ի��ض�Ŀ����������Ϊ��Ĭʾ�Ե��������������GNUͨ�ù�����Ȩ��
*	��Ӧ���յ������ڱ������GNUͨ�ù�����Ȩ�ĸ��������û�У������
*	<http://www.gnu.org/licenses/>.
* 	�������Ŀ���ñ��ļ��������Ŀ���룬�뱣�����ߡ���Ȩ�����֪ͨ�����������Ϊ��ҵĿ�����ڳ��򽻻����棬������Ϣ�б������ߣ���Ȩ����Ϣ��
*
*	This software may be used and distributed according to the terms of
*	the GNU General Public License (GPL v3), incorporated herein by reference.
*	Drivers based on or derived from this code fall under the GPL and must
*	retain the authorship, copyright and license notice.  This file is not
*	a complete program and may only be used when the entire operating
*	system is licensed under the GPL.
*
*	See the file COPYING in this distribution for more information.
*/
package com.sinelead.car.club.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.sinelead.car.club.R;
import com.sinelead.car.club.R.drawable;
import com.sinelead.car.club.R.id;

public class MapMarker implements OnGeocodeSearchListener
{
	private Context mContext;
	private AMap mAmap;
	private GeocodeSearch geocoderSearch;
	private Marker mMarker;
	private LatLng mLatLng;

	public MapMarker(Context context, AMap map, LatLng latLng)
	{
		mContext = context;
		mAmap = map;
		mLatLng = latLng;
		init();
	}
	public Marker getMarker()
	{
		return mMarker;
	}
	private void init()
	{
		// ����ʾmark��Ȼ���滻�ף�Ȼ��ʼ�������
		drawMarkers();
		queryRegeocode();
	}

	private void queryRegeocode()
	{
		geocoderSearch = new GeocodeSearch(mContext);
		geocoderSearch.setOnGeocodeSearchListener(this);
		RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(
				mLatLng.latitude, mLatLng.longitude), 200, GeocodeSearch.AMAP);

		geocoderSearch.getFromLocationAsyn(query);
	}

	private void drawMarkers()
	{
		mMarker = mAmap.addMarker(new MarkerOptions()
				.position(mLatLng)
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(mContext.getResources(),
								R.drawable.icon_openmap_focuse_mark)))
				.perspective(true).draggable(true).setFlat(true));
		mMarker.showInfoWindow();

		// ���԰Ѳ��ַŵ�mark���棬����geocoderSearch�ص���ʱ���޷��õ�marker����
		// clickMarker.setObject(arg0);
		return;
	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult arg0, int arg1)
	{
		NaviMarkerFragmentBottom markerBottomFragment = ((NaviMainActivity)mContext).getMarkerBottomFragment();
		if (markerBottomFragment != null )
		{
			markerBottomFragment.setValues(arg0);
		}
	}

	public NaviLatLng getMarkerNaviLatLng()
	{

		NaviLatLng nav = new NaviLatLng();
		nav.setLatitude(mLatLng.latitude);
		nav.setLongitude(mLatLng.longitude);
		return nav;

	}

	

	private FragmentManager getSupportFragmentManager()
	{

		return ((FragmentActivity) mContext).getSupportFragmentManager();
	}

	private Intent getIntent()
	{
		// TODO Auto-generated method stub
		return ((FragmentActivity) mContext).getIntent();
	}

	public void remove()
	{
		mMarker.remove();
		
	}
}
