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

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.j256.ormlite.dao.Dao;
import com.sinelead.car.club.R;
import com.sinelead.car.club.db.DatabaseHelper;
import com.sinelead.car.club.db.MapHistory;

public class HistoryResultList<T> extends CardWithList
{

	private List<T> mListStock;
	private Context context;
	private String key;
	private CardHeader header;

	public HistoryResultList(Context context)
	{
		super(context);
		this.context = context;

	}

	public HistoryResultList(Context context, List<T> listStock)
	{

		super(context);
		mListStock = listStock;
		this.context = context;

	}

	@Override
	protected void initCard()
	{
		setUseProgressBar(false);
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

				String title = ((MapHistory) tStock).getTitle();
				String details = ((MapHistory) tStock).getDetails();
				Integer id = ((MapHistory) tStock).getId();
				StockObject stock = new StockObject(this);
				stock.title = title;
				stock.details = details;
				stock.id = id;
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
		details.setText(stockObject.details);
		img.setImageDrawable(context.getResources().getDrawable(
				R.drawable.icon_poi_history));

		return convertView;
	}

	@Override
	public int getChildLayoutId()
	{
		return R.layout.listview_item_title_details;
	}

	public class StockObject extends DefaultListObject
	{
		public Integer id;
		public String title;
		public String details;
		public String adcode;

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
					new PoiSearchHelper(context).doSearchQuery(title, adcode);
				}
			});
			setSwipeable(true);
			// OnItemSwipeListener
			setOnItemSwipeListener(new OnItemSwipeListener()
			{
				@Override
				public void onItemSwipe(ListObject object, boolean dismissRight)
				{
					Toast.makeText(getContext(), "ɾ��" + object.getObjectId(),
							Toast.LENGTH_SHORT).show();
					DatabaseHelper helper = ((PositionSearchActivity) context)
							.getDbManager().getHelper();

					try
					{
						Dao<MapHistory, Integer> dao = helper
								.getMapHistoryDao();

						dao.deleteById(id);
					}
					catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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

		return new CustomHeader(context, R.layout.header_history);

	}

	public class CustomHeader extends CardHeader
	{

		public CustomHeader(Context context)
		{
			super(context, R.layout.header_history);
		}

		public CustomHeader(Context context, int layout)
		{
			super(context, layout);
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view)
		{
			if (view != null)
			{
				LayoutParams layoutParams = (LayoutParams) parent
						.getLayoutParams();
				layoutParams.width = LayoutParams.MATCH_PARENT;
				// layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
				view.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Toast.makeText(getContext(), "ɾ��ȫ����¼",
								Toast.LENGTH_SHORT).show();
						DatabaseHelper helper = ((PositionSearchActivity) context)
								.getDbManager().getHelper();

						try
						{
							Dao<MapHistory, Integer> dao = helper
									.getMapHistoryDao();

							dao.delete(dao.queryForAll());
							updateItems(dao.queryForAll());
						}
						catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
			}
		}
	}

	public void updateItems(List<MapHistory> historys)
	{
		mLinearListAdapter.clear();
		List<ListObject> mObjects = new ArrayList<ListObject>();

		for (MapHistory tStock : historys)
		{
			String title = null;
			String details = null;
			String adcode = null;

			title = ((MapHistory) tStock).getTitle();
			details = ((MapHistory) tStock).getDetails();
			adcode = ((MapHistory) tStock).getAdcode();

			StockObject stock = new StockObject(this);
			stock.title = title;
			stock.details = details;
			stock.adcode = adcode;
			mObjects.add(stock);
		}

		mLinearListAdapter.addAll(mObjects);

	}
}