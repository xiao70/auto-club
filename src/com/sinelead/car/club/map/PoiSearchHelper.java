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
		progDialog.setMessage("��������:\n" + key);
		progDialog.show();
	}

	private void dissmissProgressDialog()
	{
		if (progDialog != null)
		{
			progDialog.dismiss();
		}
	}

	// ����ѡ���ҷŵ�list�������û���һ��ѡ��
	private void showSuggestCity(List<SuggestionCity> cities, String key)
	{
//		String information = "�Ƽ�����\n";
//		for (int i = 0; i < cities.size(); i++)
//		{
//			cities.get(i).getSuggestionNum();
//			information += "��������:" + cities.get(i).getCityName() + "��������:"
//					+ cities.get(i).getCityCode() + "���б���:"
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
		showProgressDialog(key);// ��ʾ���ȿ�
		int currentPage = 0;
		query = new PoiSearch.Query(key, null, adcode);// ��һ��������ʾ�����ַ������ڶ���������ʾpoi�������ͣ�������������ʾpoi�������򣨿��ַ�������ȫ����
		query.setPageSize(10);// ����ÿҳ��෵�ض�����poiitem
		query.setPageNum(currentPage);// ���ò��һҳ

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
				dissmissProgressDialog();// ���ضԻ���
				if (rCode == 0)
				{
					if (result != null && result.getQuery() != null)
					{// ����poi�Ľ��
						if (result.getQuery().equals(query))
						{// �Ƿ���ͬһ��

							// ȡ����������poiitems�ж���ҳ
							List<PoiItem> poiItems = result.getPois();// ȡ�õ�һҳ��poiitem���ݣ�ҳ��������0��ʼ
							List<SuggestionCity> suggestionCities = result
									.getSearchSuggestionCitys();// ����������poiitem����ʱ���᷵�غ��������ؼ��ֵĳ�����Ϣ

							if (poiItems != null && poiItems.size() > 0)
							{

								// �л���mapActivity���б��
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
										Constant.RESULT_SEARCH, intent);// ���÷�������

								((Activity) context).finish();// �ر�Activity
							}
							else if (suggestionCities != null
									&& suggestionCities.size() > 0)
							{
								showSuggestCity(suggestionCities,
										query.getQueryString());
							}
							else
							{
								Toast.makeText(context, "û���������...",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
					else
					{
						Toast.makeText(context, "û���������...", Toast.LENGTH_SHORT)
								.show();
					}
				}
				else if (rCode == 27)
				{
					Toast.makeText(context, "�������...", Toast.LENGTH_SHORT)
							.show();
				}
				else if (rCode == 32)
				{
					Toast.makeText(context, "�ؼ��ִ���...", Toast.LENGTH_SHORT)
							.show();
				}
				else
				{

					Toast.makeText(context, "δ֪����...", Toast.LENGTH_SHORT)
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
