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

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.j256.ormlite.dao.Dao;
import com.sinelead.car.club.R;
import com.sinelead.car.club.db.DatabaseHelper;
import com.sinelead.car.club.db.MapHistory;

public class SearchResultList<T> extends CardWithList
{

	private List<T> mListStock;
	private Context context;
	private String key;
	private CardHeader header;

	public SearchResultList(Context context)
	{
		super(context);
		this.context = context;

	}

	public SearchResultList(Context context, List<T> listStock)
	{

		super(context);
		mListStock = listStock;
		this.context = context;

	}

	@Override
	protected void initCard()
	{
		setUseProgressBar(true);
		setSwipeable(false);

	}

	@Override
	protected List<ListObject> initChildren()
	{

		// Init the list
		List<ListObject> mObjects = new ArrayList<ListObject>();

		if (mListStock != null)
		{
			for (T tStock : mListStock)
			{
				String title = null;
				String district = null;
				String adcode = null;
				if (tStock instanceof Tip)
				{
					title = ((Tip) tStock).getName();
					district = ((Tip) tStock).getDistrict();
					adcode = ((Tip) tStock).getAdcode();
				}

				StockObject stock = new StockObject(this);
				stock.title = title;
				stock.district = district;
				stock.adcode = adcode;
				mObjects.add(stock);
			}

		}
		return mObjects;
	}

	@Override
	public View setupChildView(int childPosition, ListObject object,
			View convertView, ViewGroup parent)
	{

		ImageView img = (ImageView) convertView
				.findViewById(R.id.imageView_img);
		TextView title = (TextView) convertView
				.findViewById(R.id.textView_title);
		TextView details = (TextView) convertView
				.findViewById(R.id.textView_details);

		StockObject stockObject = (StockObject) object;
		title.setText(stockObject.title);
		details.setText(stockObject.district);
		img.setImageDrawable(context.getResources().getDrawable(
				R.drawable.icon_poisearch_search));

		return convertView;
	}

	@Override
	public int getChildLayoutId()
	{
		return R.layout.listview_item_title_details;
	}

	public class StockObject extends DefaultListObject
	{

		public String adcode;
		public String title;
		public String district;

		public StockObject(Card parentCard)
		{
			super(parentCard);
			init();
		}

		private void init()
		{
			// OnClick Listener
			setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(LinearListView parent, View view,
						int position, ListObject object)
				{
					DatabaseHelper helper = ((PositionSearchActivity) context)
							.getDbManager().getHelper();

					try
					{
						Dao<MapHistory, Integer> dao = helper
								.getMapHistoryDao();
						MapHistory mapHistory = new MapHistory();

						mapHistory.setTitle(title);
						mapHistory.setDetails(district);
						mapHistory.setAdcode(adcode);
						dao.createOrUpdate(mapHistory);
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					new PoiSearchHelper(context).doSearchQuery(title, adcode);

				}
			});
		}

		@Override
		public String getObjectId()
		{
			return title;
		}
	}

	@Override
	protected CardHeader initCardHeader()
	{
		// TODO Auto-generated method stub

		return null;
	}

	public void updateItems(List<Tip> tipList)
	{
		mLinearListAdapter.clear();

		List<ListObject> mObjects = new ArrayList<ListObject>();

		for (Tip tStock : tipList)
		{
			String title = null;
			String district = null;
			String adcode = null;
			title = ((Tip) tStock).getName();
			district = ((Tip) tStock).getDistrict();
			adcode = ((Tip) tStock).getAdcode();
			StockObject stock = new StockObject(this);
			stock.title = title;
			stock.district = district;
			stock.adcode = adcode;
			mObjects.add(stock);
		}

		mLinearListAdapter.addAll(mObjects);
	}

}