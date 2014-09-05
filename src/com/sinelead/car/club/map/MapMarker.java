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
		// 先显示mark，然后替换底，然后开始地理编码
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

		// 可以把布局放到mark里面，就是geocoderSearch回调的时候无法得到marker对象
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
