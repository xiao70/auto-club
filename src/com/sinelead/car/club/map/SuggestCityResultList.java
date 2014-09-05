package com.sinelead.car.club.map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.SuggestionCity;
import com.j256.ormlite.dao.Dao;
import com.sinelead.car.club.R;
import com.sinelead.car.club.db.DatabaseHelper;
import com.sinelead.car.club.db.MapHistory;

public class SuggestCityResultList<T> extends CardWithList
{

	private List<T> mListStock;
	private Context context;
	private String key;
	private CardHeader header;

	public SuggestCityResultList(Context context, List<T> listStock, String key)
	{

		super(context);
		mListStock = listStock;
		this.key = key;
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
				StockObject stock = new StockObject(this);
				stock.title = ((SuggestionCity) tStock).getCityName();
				stock.details = String.valueOf(((SuggestionCity) tStock)
						.getSuggestionNum());
				stock.adcode = ((SuggestionCity) tStock).getAdCode();
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
		details.setText("该城市搜索结果("+stockObject.details+")");
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
					new PoiSearchHelper(context).doSearchQuery(key, adcode);
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
				TextView textView = (TextView) view
						.findViewById(R.id.textView_title);
				textView.setText("当前区域没有结果,您可以选择区域。");
			}
		}
	}

}