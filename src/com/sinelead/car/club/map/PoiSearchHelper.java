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

import it.gmariotti.cardslib.library.view.CardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.sinelead.car.club.R;
import com.sinelead.car.club.constant.Constant;
import com.sinelead.car.club.db.MapHistory;

public class PoiSearchHelper
{
	private Context context;
	private ProgressDialog progDialog;
	private Query query;
	private PoiSearch poiSearch;

	public PoiSearchHelper(Context context)
	{
		this.context = context;
	}

	private void showProgressDialog(String key)
	{
		if (progDialog == null)
			progDialog = new ProgressDialog(context);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在搜索:\n" + key);
		progDialog.show();
	}

	private void dissmissProgressDialog()
	{
		if (progDialog != null)
		{
			progDialog.dismiss();
		}
	}

	// 建议选择，我放到list里面让用户再一次选择
	private void showSuggestCity(List<SuggestionCity> cities, String key)
	{
//		String information = "推荐城市\n";
//		for (int i = 0; i < cities.size(); i++)
//		{
//			cities.get(i).getSuggestionNum();
//			information += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
//					+ cities.get(i).getCityCode() + "城市编码:"
//					+ cities.get(i).getAdCode() + "\n";
//		}
//
//		Toast.makeText(context, information, Toast.LENGTH_SHORT).show();

		CardView cardView = (CardView) ((Activity) context)
				.findViewById(R.id.card_stockcard);

		SuggestCityResultList<SuggestionCity> cityCard = new SuggestCityResultList<SuggestionCity>(
				context, cities, key);
		cityCard.init();

		cardView.replaceCard(cityCard);
	}

	public void doSearchQuery(String key, String adcode)
	{
		showProgressDialog(key);// 显示进度框
		int currentPage = 0;
		query = new PoiSearch.Query(key, null, adcode);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(10);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页

		poiSearch = new PoiSearch(context, query);
		poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener()
		{

			@Override
			public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPoiSearched(PoiResult result, int rCode)
			{
				// TODO Auto-generated method stub
				dissmissProgressDialog();// 隐藏对话框
				if (rCode == 0)
				{
					if (result != null && result.getQuery() != null)
					{// 搜索poi的结果
						if (result.getQuery().equals(query))
						{// 是否是同一条

							// 取得搜索到的poiitems有多少页
							List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
							List<SuggestionCity> suggestionCities = result
									.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

							if (poiItems != null && poiItems.size() > 0)
							{

								// 切换到mapActivity进行标记
								Intent intent = new Intent(context,
										NaviMainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								intent.setAction("poi");
								Bundle bundle = new Bundle();
								bundle.putParcelableArrayList(
										"poi",
										(ArrayList<? extends Parcelable>) poiItems);

								intent.putExtras(bundle);
								intent.putExtra("key", query.getQueryString());
								((Activity) context).setResult(
										Constant.RESULT_SEARCH, intent);// 设置返回数据

								((Activity) context).finish();// 关闭Activity
							}
							else if (suggestionCities != null
									&& suggestionCities.size() > 0)
							{
								showSuggestCity(suggestionCities,
										query.getQueryString());
							}
							else
							{
								Toast.makeText(context, "没有搜索结果...",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
					else
					{
						Toast.makeText(context, "没有搜索结果...", Toast.LENGTH_SHORT)
								.show();
					}
				}
				else if (rCode == 27)
				{
					Toast.makeText(context, "网络出错...", Toast.LENGTH_SHORT)
							.show();
				}
				else if (rCode == 32)
				{
					Toast.makeText(context, "关键字错误...", Toast.LENGTH_SHORT)
							.show();
				}
				else
				{

					Toast.makeText(context, "未知错误...", Toast.LENGTH_SHORT)
							.show();
				}

			}

		});
		poiSearch.searchPOIAsyn();
	}

	public class PoiSerializable implements Serializable
	{

		private static final long serialVersionUID = 1L;
		private List<PoiItem> poiItems;

		public PoiSerializable()
		{

		}

		public List<PoiItem> getPoiItems()
		{
			return poiItems;
		}

		public void setPoiItems(List<PoiItem> poiItems)
		{
			this.poiItems = poiItems;
		}

	}
}
